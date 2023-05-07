package com.suspend.android.learntodrive.data.datasource.question_simulation

import com.suspend.android.learntodrive.model.QuestionSimulationEntity
import io.reactivex.rxjava3.core.Single

interface IQuestionSimulationDataSource {

    interface Local {
        fun getAll(): Single<List<QuestionSimulationEntity>>
        fun getQuestionSimulationByID(id: Int): Single<QuestionSimulationEntity>
        fun getQuestionSimulationByType(type: Int): Single<List<QuestionSimulationEntity>>
    }
}