package com.suspend.android.learntodrive.data.repository.question_type

import com.suspend.android.learntodrive.data.datasource.question_type.IQuestionTypeDataSource
import com.suspend.android.learntodrive.model.QuestionTypeEntity
import io.reactivex.rxjava3.core.Single

class QuestionTypeRepository(private val local: IQuestionTypeDataSource.Local) :
    IQuestionTypeRepository {

    override fun getAll(): Single<List<QuestionTypeEntity>> {
        return local.getAll()
    }
}
