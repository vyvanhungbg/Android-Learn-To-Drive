package com.suspend.android.learntodrive.data.repository.question

import com.suspend.android.learntodrive.model.QuestionEntity
import io.reactivex.rxjava3.core.Single

interface IQuestionRepository {

    fun getAll(): Single<List<QuestionEntity>>
    fun getQuestionByID(idQuestion:Int): Single<QuestionEntity>
    fun getQuestionByLicense(typeQuestion: QuestionEntity.Companion.TYPE): Single<List<QuestionEntity>>
    fun getQuestionDieByLicense(typeQuestion: QuestionEntity.Companion.TYPE): Single<List<QuestionEntity>>
    fun getAllDieQuestion(): Single<List<QuestionEntity>>
}