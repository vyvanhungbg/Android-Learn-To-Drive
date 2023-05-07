package com.suspend.android.learntodrive.data.datasource.question_simulation

import com.suspend.android.learntodrive.data.database.system.DatabaseLocal
import com.suspend.android.learntodrive.model.QuestionSimulationEntity
import io.reactivex.rxjava3.core.Single

class QuestionSimulationLocalDataSource(private val database: DatabaseLocal) :
    IQuestionSimulationDataSource.Local {

    override fun getAll(): Single<List<QuestionSimulationEntity>> {
        return database.questionSimulationDao.getAll()
    }

    override fun getQuestionSimulationByID(id: Int): Single<QuestionSimulationEntity> {
        return database.questionSimulationDao.getQuestionSimulationByID(id)
    }

    override fun getQuestionSimulationByType(type: Int): Single<List<QuestionSimulationEntity>> {
        return database.questionSimulationDao.getQuestionSimulationByType(type)
    }
}