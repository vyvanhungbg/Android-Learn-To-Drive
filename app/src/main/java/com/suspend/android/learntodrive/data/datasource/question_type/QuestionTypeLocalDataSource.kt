package com.suspend.android.learntodrive.data.datasource.question_type

import com.suspend.android.learntodrive.data.database.system.DatabaseLocal
import com.suspend.android.learntodrive.model.QuestionTypeEntity
import io.reactivex.rxjava3.core.Single

class QuestionTypeLocalDataSource(
    private val database: DatabaseLocal
): IQuestionTypeDataSource.Local {

    override fun getAll(): Single<List<QuestionTypeEntity>> {
        return  database.questionTypeDao.getAll();
    }
}
