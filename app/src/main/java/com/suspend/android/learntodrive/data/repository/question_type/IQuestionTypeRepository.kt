package com.suspend.android.learntodrive.data.repository.question_type

import com.suspend.android.learntodrive.model.QuestionTypeEntity
import io.reactivex.rxjava3.core.Single

interface IQuestionTypeRepository {

    fun getAll(): Single<List<QuestionTypeEntity>>
}
