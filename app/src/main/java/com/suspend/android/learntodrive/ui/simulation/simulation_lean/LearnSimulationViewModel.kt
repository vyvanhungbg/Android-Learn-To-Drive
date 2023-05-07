package com.suspend.android.learntodrive.ui.simulation.simulation_lean

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.suspend.android.learntodrive.base.BaseViewModel
import com.suspend.android.learntodrive.data.repository.question_simulation_type.IQuestionSimulationTypeRepository
import com.suspend.android.learntodrive.model.QuestionSimulationTypeEntity
import com.suspend.android.learntodrive.utils.extension.logError

private const val TAG = "LearnSimulationViewMode"

class LearnSimulationViewModel(
    private val questionSimulationTypeRepository: IQuestionSimulationTypeRepository
) : BaseViewModel() {

    private val _simulationQuestionTypeList = MutableLiveData<List<QuestionSimulationTypeEntity>>()
    val simulationQuestionTypeList: LiveData<List<QuestionSimulationTypeEntity>>
        get() = _simulationQuestionTypeList

    fun getQuestionSimulationTypeList() {
        registerDisposable(
            executeTask(
                task = {
                    questionSimulationTypeRepository.getAll()
                },
                onSuccess = {
                    _simulationQuestionTypeList.value = it
                },
                onError = {
                    logError(TAG, it.toString())
                }
            )
        )
    }
}