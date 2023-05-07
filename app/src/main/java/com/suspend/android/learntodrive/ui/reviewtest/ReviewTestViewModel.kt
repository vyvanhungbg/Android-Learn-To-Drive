package com.suspend.android.learntodrive.ui.reviewtest

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.suspend.android.learntodrive.base.BaseViewModel
import com.suspend.android.learntodrive.data.repository.test.ITestRepository
import com.suspend.android.learntodrive.model.Question
import com.suspend.android.learntodrive.model.TestEntity
import com.suspend.android.learntodrive.utils.extension.getVoiceSpeechSettings
import com.suspend.android.learntodrive.utils.extension.logError
import com.suspend.android.learntodrive.utils.util.SingleLiveEvent

private const val TAG = "ReviewTestViewModel"

class ReviewTestViewModel(
    private val testRepository: ITestRepository,
    private val sharedPreferencesSettings: SharedPreferences
) : BaseViewModel() {


    private val _questions = MutableLiveData<List<Question>>()
    val questions: LiveData<List<Question>>
        get() = _questions

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private val _positionCurrentQuestion = MutableLiveData(0)
    val positionCurrentQuestion: LiveData<Int>
        get() = _positionCurrentQuestion

    private val _voiceSpeech = SingleLiveEvent<Boolean>()
    val voiceSpeech: LiveData<Boolean>
        get() = _voiceSpeech

    init {
        _voiceSpeech.value = sharedPreferencesSettings.getVoiceSpeechSettings()
    }

    fun getTestByID(id: Int) {
        registerDisposable(
            executeTask(
                task = { testRepository.getByID(id) },
                onSuccess = {
                    _questions.value = it.questions ?: mutableListOf()
                    _positionCurrentQuestion.value = 0 //  update get question successfully
                },
                onError = {
                    logError(TAG, it.message)
                }
            )
        )
    }

    fun getTestEntity(testEntity: TestEntity) {
        _questions.value = testEntity.questions ?: mutableListOf()
        _positionCurrentQuestion.value = 0 //  update get question successfully
    }

    fun setVoiceSpeechQuestion(checked: Boolean) {
        _voiceSpeech.value = checked
    }

    fun nextQuestion() {
        val currentPosition = _positionCurrentQuestion.value
        val totalQuestion = questions.value?.size
        if (currentPosition != null && totalQuestion != null && totalQuestion - 1 > currentPosition)
            _positionCurrentQuestion.value = currentPosition.inc()
    }

    fun previousQuestion() {
        val currentPosition = _positionCurrentQuestion.value
        if (currentPosition != null && currentPosition > 0)
            _positionCurrentQuestion.value = currentPosition.dec()
    }

    fun getQuestionByPosition(currentPosition: Int?): Question? {
        val listQuestion = questions.value
        if (currentPosition != null && !listQuestion.isNullOrEmpty() && currentPosition < listQuestion.size) {
            val question = listQuestion[currentPosition]
            val positionAnswerChecked = getPositionAnswerChecked(question.id)
            val answerList =
                question.answer.map { if (it.position == positionAnswerChecked) it.copy(checked = true) else it }
            return question.copy(answer = answerList)
        }
        return null
    }

    private fun getPositionAnswerChecked(questionID: Int): Int {
        val mTest = questions.value?.find { it.id == questionID }
        return mTest?.answerSelected ?: 0
    }

    fun changePosition(position: Int) {
        _positionCurrentQuestion.value = position
    }

    fun changeQuestion(question: Question) {
        _question.value = question
    }

    fun setCurrentQuestionSelected(currentQuestion: Question): List<Question>? {
        val currentListQuestion = questions.value?.toMutableList()
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

    fun updateListQuestionInTest(newListQuestion: MutableList<Question>) {
        questions.value?.let {
            _questions.value = newListQuestion
        }
    }
}