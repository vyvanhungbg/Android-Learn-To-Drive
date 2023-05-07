package com.suspend.android.learntodrive.ui.preparetest

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.suspend.android.learntodrive.base.BaseViewModel
import com.suspend.android.learntodrive.data.repository.exam_set.IExamSetRepository
import com.suspend.android.learntodrive.data.repository.license.ILicenseRepository
import com.suspend.android.learntodrive.data.repository.question.IQuestionRepository
import com.suspend.android.learntodrive.model.ExamSet
import com.suspend.android.learntodrive.model.LicenseEntity
import com.suspend.android.learntodrive.model.QuestionEntity
import com.suspend.android.learntodrive.model.QuestionTypeEntity
import com.suspend.android.learntodrive.model.Test
import com.suspend.android.learntodrive.model.TestEntity
import com.suspend.android.learntodrive.model.toQuestion
import com.suspend.android.learntodrive.ui.test.TYPE
import com.suspend.android.learntodrive.utils.extension.getCurrentLicenseName
import com.suspend.android.learntodrive.utils.extension.getCurrentLicenseType
import com.suspend.android.learntodrive.utils.extension.logError
import io.reactivex.rxjava3.core.Single

private const val TAG = "PrepareTestViewModel"

class PrepareTestViewModel(
    private val questionRepository: IQuestionRepository,
    private val licenseRepository: ILicenseRepository,
    private val examSetRepository: IExamSetRepository,
    private val sharedPreferencesSettings: SharedPreferences
) : BaseViewModel() {

    private val _license = MutableLiveData<LicenseEntity>()
    val license: LiveData<LicenseEntity>
        get() = _license

    private val _questions = MutableLiveData<List<QuestionEntity>>()
    val questions: LiveData<List<QuestionEntity>>
        get() = _questions

    private val _test = MutableLiveData<Test>()
    val test: LiveData<Test>
        get() = _test


    /*private fun getQuestions() {
        registerDisposable(
            executeTask(
                task = { questionRepository.getAll() },
                onSuccess = {
                    Log.e("hung", it.size.toString())
                    _questions.value = it
                },
                onError = {
                    Log.e("hung", it.toString())
                }
            )
        )
    }*/

    fun getCurrentLicense(
        bundleType: TYPE,
        bundleIndexExamSet: Int,
        bundleItemQuestionType: QuestionTypeEntity?
    ) {
        registerDisposable(
            executeTask(
                task = { licenseRepository.getLicenseByName(sharedPreferencesSettings.getCurrentLicenseName()) },
                onSuccess = {
                    _license.value = it
                    if (bundleType == TYPE.TEST && bundleIndexExamSet > 0) { // mode test by exam test
                        getTestByExamSet(it, bundleIndexExamSet)
                    }
                    if (bundleType == TYPE.TEST) {  // mode test random
                        getQuestionForTest(it)
                    } else {
                        getQuestionForLearn(bundleItemQuestionType)  // mode learn
                    }
                },
                onError = {
                    logError(TAG, it.toString())
                }
            )
        )
    }

    private fun getQuestionForTest(license: LicenseEntity) {
        val numberOfQuestionPerTest = license.numberOfQuestionPerTest
        val numberOfCorrectQuestionPerTest = license.numberOfCorrectQuestionPerTest
        val timeTest = license.duration
        registerDisposable(
            executeTask(
                task = { questionRepository.getQuestionByLicense(sharedPreferencesSettings.getCurrentLicenseType()) },
                onSuccess = {
                    val question = it.shuffled()
                        .take(numberOfQuestionPerTest)
                        .map { item -> item.toQuestion() }
                    val test =
                        Test(
                            sharedPreferencesSettings.getCurrentLicenseName(),
                            numberOfCorrectQuestionPerTest,
                            question,
                            timeTest
                        )
                    logError(
                        TAG,
                        "Bài thi : Đã lấy bộ ${license.name} gồm ${it.size.toString()} câu"
                    )
                    _test.value = test
                },
                onError = {
                    logError(TAG, it.toString())
                }
            )
        )
    }


    private fun getTestByExamSet(license: LicenseEntity, indexExamSet: Int) {
        registerDisposable(
            executeTask(
                task = {
                    examSetRepository.getExamSetByIdLicenseAndIndexExam(
                        sharedPreferencesSettings.getCurrentLicenseName(),
                        indexExamSet
                    )
                },
                onSuccess = {
                    getQuestionsByListIDQuestion(it.map { item->item.idQuestion },license,indexExamSet)
                },
                onError = {
                    logError(TAG, it.toString())
                },
                loadingInvisible = false
            )
        )
    }

    private fun getQuestionsByListIDQuestion(listIDQuestion: List<Int>,license: LicenseEntity,indexExamSet: Int) {
        val iterableSource = listIDQuestion.map {
            questionRepository.getQuestionByID(
                it
            )
        }
        registerDisposable(
            executeTask(
                task = {
                    Single.zip(iterableSource) {

                        return@zip it.map { item -> item as? QuestionEntity }
                    }
                },
                onSuccess = {
                    val numberOfQuestionPerTest = license.numberOfQuestionPerTest
                    val numberOfCorrectQuestionPerTest = license.numberOfCorrectQuestionPerTest
                    val timeTest = license.duration
                    val questionTmp = it.shuffled()
                        .take(numberOfQuestionPerTest)
                        .map { item -> item?.toQuestion() }

                    // kiểm tra đủ số lượng câu hỏi sau khi đã lọc null
                    val question = questionTmp.filterNotNull().toMutableList()
                    // thêm câu hỏi nếu thiếu
                    while (question.size < numberOfQuestionPerTest){
                        val questionRandom = question.random()
                        question.add(questionRandom)
                    }
                    val test =
                        Test(
                            sharedPreferencesSettings.getCurrentLicenseName() + " (Đề số $indexExamSet)",
                            numberOfCorrectQuestionPerTest,
                            question,
                            timeTest,
                            indexExamSet
                        )
                    logError(
                        TAG,
                        "Bài thi : Đã lấy bộ ${license.name} gồm ${it.size.toString()} câu"
                    )
                    _test.value = test
                },
                onError = {
                    logError(TAG, it.toString())
                }
            )
        )
    }

    private fun getQuestionForLearn(bundleItemQuestionType: QuestionTypeEntity?) {
        registerDisposable(
            executeTask(
                task = { questionRepository.getQuestionByLicense(sharedPreferencesSettings.getCurrentLicenseType()) },
                onSuccess = {
                    val question =
                        it.filter { item -> item.questionType == bundleItemQuestionType?.id }
                            .map { item -> item.toQuestion() }
                    val test =
                        Test(
                            bundleItemQuestionType?.name ?: "",
                            question.size,
                            question,
                            question.size * 3
                        )
                    logError(
                        TAG,
                        "Đã lấy bộ bài học gồm ${it.size.toString()}"
                    )
                    _test.value = test
                },
                onError = {
                    logError(TAG, it.toString())
                }
            )
        )
    }
}