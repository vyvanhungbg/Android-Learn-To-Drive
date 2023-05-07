package com.suspend.android.learntodrive.ui.wronganswer

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.suspend.android.learntodrive.base.BaseViewModel
import com.suspend.android.learntodrive.data.repository.test.ITestRepository
import com.suspend.android.learntodrive.model.Question
import com.suspend.android.learntodrive.model.TestEntity
import com.suspend.android.learntodrive.utils.extension.getCurrentLicenseName
import com.suspend.android.learntodrive.utils.extension.getCurrentLicenseType
import com.suspend.android.learntodrive.utils.extension.logError

private const val TAG = "WrongAnswerViewModel"

class WrongAnswerViewModel(
    private val testRepository: ITestRepository,
    private val sharedPreferencesSettings: SharedPreferences
) : BaseViewModel() {

    private val _tests = MutableLiveData<List<TestEntity>>()
    val tests: LiveData<List<TestEntity>>
        get() = _tests

    private val _questions = MediatorLiveData<List<Question>>()
    val questions: LiveData<List<Question>>
        get() = _questions

    fun getAllQuestionsDie() {
        registerDisposable(
            executeTask(
                task = { testRepository.getAll(sharedPreferencesSettings.getCurrentLicenseName()) },
                onSuccess = {
                    _tests.value = it
                },
                onError = {
                    logError(TAG, it.message)
                }
            )
        )
    }
}