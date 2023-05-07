package com.suspend.android.learntodrive.data.repository.question

import com.suspend.android.learntodrive.data.datasource.question.IQuestionDataSource
import com.suspend.android.learntodrive.model.QuestionEntity
import io.reactivex.rxjava3.core.Single

class QuestionRepository(
    private val local: IQuestionDataSource.Local
) : IQuestionRepository {

    override fun getAll(): Single<List<QuestionEntity>> {
        return local.getAll()
    }

    override fun getQuestionByID(idQuestion: Int): Single<QuestionEntity> {
        return local.getQuestionByID(idQuestion)
    }

    override fun getQuestionByLicense(typeQuestion: QuestionEntity.Companion.TYPE): Single<List<QuestionEntity>> {
        return local.getQuestionByLicense(typeQuestion)
    }

    override fun getQuestionDieByLicense(typeQuestion: QuestionEntity.Companion.TYPE): Single<List<QuestionEntity>> {
        return local.getQuestionDieByLicense(typeQuestion)
    }

    override fun getAllDieQuestion(): Single<List<QuestionEntity>> {
        return local.getAllDieQuestion()
    }
}