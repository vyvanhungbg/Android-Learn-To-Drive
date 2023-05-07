package com.suspend.android.learntodrive.data.datasource.question

import com.suspend.android.learntodrive.data.database.system.DatabaseLocal
import com.suspend.android.learntodrive.model.QuestionEntity
import io.reactivex.rxjava3.core.Single

class QuestionLocalDataSource(private val database: DatabaseLocal): IQuestionDataSource.Local {

    override fun getAll(): Single<List<QuestionEntity>> {
        return database.questionDao.getAll()
    }

    override fun getQuestionByID(idQuestion: Int): Single<QuestionEntity> {
        return  database.questionDao.getQuestionByID(idQuestion)
    }

    override fun getQuestionByLicense(typeQuestion: QuestionEntity.Companion.TYPE): Single<List<QuestionEntity>> {
        return when(typeQuestion){
            QuestionEntity.Companion.TYPE.TYPE_200 -> database.questionDao.getQuestionByType200()
            QuestionEntity.Companion.TYPE.TYPE_450 -> database.questionDao.getQuestionByType450()
            QuestionEntity.Companion.TYPE.TYPE_500 -> database.questionDao.getQuestionByType500()
            QuestionEntity.Companion.TYPE.TYPE_574 -> database.questionDao.getQuestionByType574()
            QuestionEntity.Companion.TYPE.TYPE_600 -> database.questionDao.getQuestionByType600()
        }
    }

    override fun getQuestionDieByLicense(typeQuestion: QuestionEntity.Companion.TYPE): Single<List<QuestionEntity>> {
        return when(typeQuestion){
            QuestionEntity.Companion.TYPE.TYPE_200 -> database.questionDao.getQuestionDieByType200()
            QuestionEntity.Companion.TYPE.TYPE_450 -> database.questionDao.getQuestionDieByType450()
            QuestionEntity.Companion.TYPE.TYPE_500 -> database.questionDao.getQuestionDieByType500()
            QuestionEntity.Companion.TYPE.TYPE_574 -> database.questionDao.getQuestionDieByType574()
            QuestionEntity.Companion.TYPE.TYPE_600 -> database.questionDao.getQuestionDieByType600()
        }
    }

    override fun getAllDieQuestion(): Single<List<QuestionEntity>> {
        return database.questionDao.getAllDieQuestion()
    }

}
