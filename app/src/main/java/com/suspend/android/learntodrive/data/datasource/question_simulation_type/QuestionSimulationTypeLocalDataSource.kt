package com.suspend.android.learntodrive.data.datasource.question_simulation_type

import com.suspend.android.learntodrive.data.database.system.DatabaseLocal
import com.suspend.android.learntodrive.model.QuestionSimulationTypeEntity
import io.reactivex.rxjava3.core.Single

class QuestionSimulationTypeLocalDataSource(private val database: DatabaseLocal) :
    IQuestionSimulationTypeDataSource.Local {

    override fun getAll(): Single<List<QuestionSimulationTypeEntity>> {
        return database.questionSimulationTypeDao.getAll()
    }

}