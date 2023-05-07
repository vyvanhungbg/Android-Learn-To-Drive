package com.suspend.android.learntodrive.data.datasource.question_type

import com.suspend.android.learntodrive.model.QuestionTypeEntity
import io.reactivex.rxjava3.core.Single

interface IQuestionTypeDataSource {

    interface Local {
        fun getAll(): Single<List<QuestionTypeEntity>>
    }
}
