package com.suspend.android.learntodrive.ui.home

import android.content.Context
import android.content.SharedPreferences
import android.speech.tts.TextToSpeech
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.suspend.android.learntodrive.R
import com.suspend.android.learntodrive.base.BaseViewModel
import com.suspend.android.learntodrive.data.repository.itemhome.IItemHomeRepository
import com.suspend.android.learntodrive.data.repository.question.IQuestionRepository
import com.suspend.android.learntodrive.data.repository.test.ITestRepository
import com.suspend.android.learntodrive.model.ItemHome
import com.suspend.android.learntodrive.model.QuestionEntity
import com.suspend.android.learntodrive.model.Slider
import com.suspend.android.learntodrive.model.TestEntity
import com.suspend.android.learntodrive.utils.extension.getCurrentLicenseName
import com.suspend.android.learntodrive.utils.extension.logError

private const val TAG = "HomeViewModel"

class HomeViewModel(
    private val questionRepository: IQuestionRepository,
    private val testRepository: ITestRepository,
    private val itemHomeRepository: IItemHomeRepository,
    private val sharedPreferencesSettings: SharedPreferences,
    private val tts: TextToSpeech // khởi tạo tts sớm cb cho màn test
) : BaseViewModel() {

    private val _questions = MutableLiveData<List<QuestionEntity>>()
    val questions: LiveData<List<QuestionEntity>>
        get() = _questions

    private val _itemHome = MutableLiveData<List<ItemHome>>()
    val itemHome: LiveData<List<ItemHome>>
        get() = _itemHome

    private val _slides = MutableLiveData<List<Slider>?>()
    val slides: LiveData<List<Slider>?> = _slides

    private val _tests = MutableLiveData<List<TestEntity>>()
    val tests: LiveData<List<TestEntity>>
        get() = _tests


    fun getAllQuestionsStatistic() {
        registerDisposable(
            executeTask(
                task = { testRepository.getAll(sharedPreferencesSettings.getCurrentLicenseName()) },
                onSuccess = {
                    _tests.value = it
                },
                onError = {
                    logError(TAG, it.message)
                }, loadingInvisible = false
            )
        )
    }

    fun calcTotalQuestions(): Int {

        val totalQuestionCorrect = mutableSetOf<Int>()
        tests.value?.forEach { test ->
            val listQuestionCorrect =
                test.questions?.filter { it.answerSelected == it.answerCorrectPosition }
                    ?.map { it -> it.id }
            if (listQuestionCorrect != null) {
                totalQuestionCorrect.addAll(listQuestionCorrect)
            }
        }
        return totalQuestionCorrect.size
    }

    fun calcTotalTests(): Int {
        return tests.value?.size ?: 0
    }

    fun calcPercentPassExams(): Int {
        val totalTests = calcTotalTests()
        val totalExamsPass = tests.value?.count { it.passTest } ?: 0
        if (totalTests == 0 || totalExamsPass == 0) return 0
        return (totalExamsPass *1.0 / totalTests * 100).toInt()
    }

    fun getItemHome(context: Context) {
        _itemHome.value = itemHomeRepository.getAllItemHome(context)
    }


    fun getSlider() {
        val list = mutableListOf<Slider>()
        list.add(Slider(R.drawable.image_sa_hinh))
        list.add(Slider(R.drawable.image_slide_1))
        list.add(Slider(R.drawable.image_sa_hinh))
        _slides.value = list
    }
}
