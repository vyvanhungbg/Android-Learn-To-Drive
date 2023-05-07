package com.suspend.android.learntodrive.ui.test

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.suspend.android.learntodrive.base.BaseViewModel
import com.suspend.android.learntodrive.data.repository.question.IQuestionRepository
import com.suspend.android.learntodrive.model.Answer
import com.suspend.android.learntodrive.model.Question
import com.suspend.android.learntodrive.model.ResultTest
import com.suspend.android.learntodrive.model.Test
import com.suspend.android.learntodrive.utils.constant.Constant
import com.suspend.android.learntodrive.utils.extension.convertToSecond
import com.suspend.android.learntodrive.utils.extension.getVoiceSpeechSettings
import com.suspend.android.learntodrive.utils.extension.withIOToMainThread
import com.suspend.android.learntodrive.utils.util.SingleLiveEvent
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit

private const val TAG = "TestViewModel"

class TestViewModel(
    private val questionRepository: IQuestionRepository,
    private val sharedPreferencesSettings: SharedPreferences
) : BaseViewModel() {

    private val _test = MutableLiveData<Test>()
    val test: LiveData<Test>
        get() = _test

    private val _positionCurrentQuestion = MutableLiveData(0)
    val positionCurrentQuestion: LiveData<Int>
        get() = _positionCurrentQuestion

    private val _countDown = MutableLiveData<Long>()
    val countDown: LiveData<Long>
        get() = _countDown

    private val _finishCountDown = SingleLiveEvent<Boolean>()
    val finishCountDown: LiveData<Boolean>
        get() = _finishCountDown

    private val _voiceSpeech = SingleLiveEvent<Boolean>()
    val voiceSpeech: LiveData<Boolean>
        get() = _voiceSpeech

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    fun changeQuestion(question: Question) {
        _question.value = question
    }

    fun changePosition(position: Int) {
        _positionCurrentQuestion.value = position
    }

    init {
        _voiceSpeech.value = sharedPreferencesSettings.getVoiceSpeechSettings()
    }

    fun intiTest(test: Test) {
        _test.value = test
        //getQuestions()
        countDown()
    }

    fun setVoiceSpeechQuestion(checked: Boolean) {
        _voiceSpeech.value = checked
    }

    fun nextQuestion() {
        val currentPosition = _positionCurrentQuestion.value
        val totalQuestion = test.value?.questions?.size
        if (currentPosition != null && totalQuestion != null && totalQuestion - 1 > currentPosition)
            _positionCurrentQuestion.value = currentPosition.inc()
    }

    fun previousQuestion() {
        val currentPosition = _positionCurrentQuestion.value
        if (currentPosition != null && currentPosition > 0)
            _positionCurrentQuestion.value = currentPosition.dec()
    }

    // set vị trí người dùng chọn câu trả lời lưu vào list
    fun setSelectedAnswer(answer: Answer) {
        val currentQuestion = question.value
        currentQuestion?.let {
            val positionAnswerSelected =
                if (answer.checked) answer.position else Question.DEFAULT_ANSWER_SELECTED

            val currentPositionQuestion = _positionCurrentQuestion.value
            val oldList = test.value?.questions
            if (currentPositionQuestion != null && !oldList.isNullOrEmpty()) {
                val newItem =
                    oldList[currentPositionQuestion].copy(answerSelected = positionAnswerSelected)
                val newList = oldList.toMutableList()
                newList[currentPositionQuestion] = newItem
                updateListQuestionInTest(newList)
            }
        }
    }

    fun setCurrentQuestionSelected(currentQuestion: Question): List<Question>? {
        val currentListQuestion = test.value?.questions?.toMutableList()
        val list = currentListQuestion?.map { item ->
            if (currentQuestion.id == item.id)
                item.copy(currentSelected = true)
            else item.copy(
                currentSelected = false
            )
        }
        list?.let {
            updateListQuestionInTest(it.toMutableList())
        }
        return list
    }

    // set border đỏ cho câu hỏi đang được lựa chọn lưu vào question trong test
    fun updateListQuestionInTest(newListQuestion: MutableList<Question>) {
        val currentTest = test.value
        currentTest?.let {
            _test.value = it.copy(questions = newListQuestion)
        }
    }

    // lấy vị trí câu trả lời đã chọn để set lại vị chí chọn cho người dùng quay lại xem câu hỏi
    private fun getPositionAnswerChecked(questionID: Int): Int {
        val mTest = test.value?.questions?.find { it.id == questionID }
        return mTest?.answerSelected ?: 0
    }

    // lấy question và set lại trang thái câu trả lời
    fun getQuestionByPosition(currentPosition: Int?): Question? {
        val listQuestion = test.value?.questions
        if (currentPosition != null && !listQuestion.isNullOrEmpty() && currentPosition < listQuestion.size) {
            val question = listQuestion[currentPosition]
            val positionAnswerChecked = getPositionAnswerChecked(question.id)
            val answerList =
                question.answer.map { if (it.position == positionAnswerChecked) it.copy(checked = true) else it }
            return question.copy(answer = answerList)
        }
        return null
    }

    fun calcCoreOfTest(bundleTest: Test?): ResultTest? {
        val mTest = test.value
        return if (mTest != null) {
            val correctAnswer =
                mTest.questions.count { it.answerSelected == it.answerCorrectPosition }
            val ignoreAnswer = mTest.questions.count { it.answerSelected == 0 }
            val wrongAnswer = mTest.questions.size - correctAnswer - ignoreAnswer
            var pass = correctAnswer >= mTest.minimumQuestion
            var reason = ""
            if (pass) {
                val failedDieQuestion = mTest.questions.filter { it.questionDie }
                    .any { it.answerCorrectPosition != it.answerSelected }
                if (failedDieQuestion) {
                    pass = false
                    reason = "Sai câu điểm liệt"
                }else{
                    reason = "Vượt qua"
                }
            } else {
                reason = "Chưa đạt ${mTest.minimumQuestion} câu"
            }

            var time = mTest.time * 60L
            countDown.value?.let {
                time -= it
            }
            ResultTest(correctAnswer, wrongAnswer, ignoreAnswer, time, reason, pass, test.value, indexExam = bundleTest?.indexExam)
        } else {
            null
        }
    }

    private fun countDown() {
        _test.value?.time?.let { time ->
            registerDisposable(
                Observable.interval(Constant.TIME.SECOND_TO_MILLI, TimeUnit.MILLISECONDS)
                    .withIOToMainThread()
                    .takeUntil { it == time.convertToSecond() }
                    .map { it -> time.convertToSecond() - it }
                    .doOnComplete {
                        _finishCountDown.value = true
                    }.subscribe(
                        { _countDown.value = it },
                        { }
                    )
            )
        }
    }

    override fun onCleared() {
        Log.e(TAG, "onCleared: TestViewModel")
        super.onCleared()
    }

}
