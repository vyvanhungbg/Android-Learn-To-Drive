package com.suspend.android.learntodrive.data.datasource.question_simulation_type

import com.suspend.android.learntodrive.model.QuestionSimulationTypeEntity
import io.reactivex.rxjava3.core.Single

interface IQuestionSimulationTypeDataSource {

    interface Local {
        fun getAll(): Single<List<QuestionSimulationTypeEntity>>
    }
}