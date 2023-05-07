package com.suspend.android.learntodrive.ui.list_exam_set

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.suspend.android.learntodrive.base.BaseViewModel
import com.suspend.android.learntodrive.data.repository.exam_set.IExamSetRepository
import com.suspend.android.learntodrive.data.repository.test.ITestRepository
import com.suspend.android.learntodrive.model.ExamSet
import com.suspend.android.learntodrive.model.TestEntity
import com.suspend.android.learntodrive.utils.extension.getCurrentLicenseName
import com.suspend.android.learntodrive.utils.extension.logError
import io.reactivex.rxjava3.core.Single

private const val TAG = "ListExamTestViewModel"

class ListExamTestViewModel(
    private val examSetRepository: IExamSetRepository,
    private val testRepository: ITestRepository,
    private val sharedPreferencesSettings: SharedPreferences
) : BaseViewModel() {

    private val _examSet = MutableLiveData<List<ExamSet>>()
    val examSet: LiveData<List<ExamSet>>
        get() = _examSet

    fun getExamSetList() {
        registerDisposable(
            executeTask(
                task = {
                    examSetRepository.getExamTestByIDLicense(sharedPreferencesSettings.getCurrentLicenseName())
                },
                onSuccess = {
                    val listIdTest = it.map { item -> item.indexExam }.toSet().toList()
                    getStatisticExamTest(listIdTest)
                },
                onError = {
                    logError(TAG, it.toString())
                }
            )
        )
    }

    private fun getStatisticExamTest(listIndexTest: List<Int>) {
        val iterableSource = listIndexTest.map {
            testRepository.getTestByTypeAndIndexExam(
                sharedPreferencesSettings.getCurrentLicenseName(),
                it
            )
        }
        registerDisposable(
            executeTask(
                task = {
                    Single.zip(iterableSource) {

                        return@zip it.map { item -> item as? List<TestEntity> }
                    }
                },
                onSuccess = {
                    val examSetList: MutableList<ExamSet> = mutableListOf()
                    listIndexTest.forEach { index->
                        val listTest = it.find { item -> item?.firstOrNull()?.indexExam == index }
                        examSetList.add(calTestEntityToExamSet(index, listTest))
                    }

                    examSetList.sortBy { item -> item.id }
                    _examSet.value = examSetList
                },
                onError = {
                    logError(TAG, it.toString())
                }
            )
        )
    }

    private fun calTestEntityToExamSet(indexExam: Int, list: List<TestEntity>?): ExamSet {
        val numberOfTestPassed = list?.count { item -> item.passTest }
        val numberOfTestFailed = list?.count { item -> item.passTest.not() }
        return ExamSet(
            indexExam,
            list?.firstOrNull()?.questions ?: listOf(),
            numberOfTestFailed = numberOfTestFailed ?: 0,
            numberOfTestPassed = numberOfTestPassed ?: 0
        )
    }
}