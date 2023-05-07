package com.suspend.android.learntodrive.data.repository.question_simulation

import com.suspend.android.learntodrive.model.QuestionSimulationEntity
import io.reactivex.rxjava3.core.Single

interface IQuestionSimulationRepository {

    fun getAll(): Single<List<QuestionSimulationEntity>>
    fun getQuestionSimulationByID(id: Int): Single<QuestionSimulationEntity>
    fun getQuestionSimulationByType(type: Int): Single<List<QuestionSimulationEntity>>
}