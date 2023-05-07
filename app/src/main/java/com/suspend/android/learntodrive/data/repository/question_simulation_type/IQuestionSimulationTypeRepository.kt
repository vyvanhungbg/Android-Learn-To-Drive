package com.suspend.android.learntodrive.data.repository.question_simulation_type

import com.suspend.android.learntodrive.model.QuestionSimulationTypeEntity
import io.reactivex.rxjava3.core.Single

interface IQuestionSimulationTypeRepository {

    fun getAll(): Single<List<QuestionSimulationTypeEntity>>
}