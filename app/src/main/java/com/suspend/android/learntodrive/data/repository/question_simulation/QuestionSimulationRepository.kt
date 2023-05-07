package com.suspend.android.learntodrive.data.repository.question_simulation

import com.suspend.android.learntodrive.data.datasource.question_simulation.IQuestionSimulationDataSource
import com.suspend.android.learntodrive.model.QuestionSimulationEntity
import io.reactivex.rxjava3.core.Single

class QuestionSimulationRepository(private val local: IQuestionSimulationDataSource.Local) :
    IQuestionSimulationRepository {

    override fun getAll(): Single<List<QuestionSimulationEntity>> {
        return local.getAll()
    }

    override fun getQuestionSimulationByID(id: Int): Single<QuestionSimulationEntity> {
        return local.getQuestionSimulationByID(id)
    }

    override fun getQuestionSimulationByType(type: Int): Single<List<QuestionSimulationEntity>> {
        return local.getQuestionSimulationByType(type)
    }
}