package com.suspend.android.learntodrive.data.repository.question_simulation_type

import com.suspend.android.learntodrive.data.datasource.question_simulation_type.IQuestionSimulationTypeDataSource
import com.suspend.android.learntodrive.model.QuestionSimulationTypeEntity
import io.reactivex.rxjava3.core.Single

class QuestionSimulationTypeRepository(private val local: IQuestionSimulationTypeDataSource.Local) :
    IQuestionSimulationTypeRepository {

    override fun getAll(): Single<List<QuestionSimulationTypeEntity>> {
        return local.getAll()
    }
}