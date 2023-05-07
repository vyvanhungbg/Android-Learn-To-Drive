package com.suspend.android.learntodrive.data.database.system.dao

import androidx.room.Dao
import androidx.room.Query
import com.suspend.android.learntodrive.model.QuestionTypeEntity
import com.suspend.android.learntodrive.utils.constant.Constant
import io.reactivex.rxjava3.core.Single

@Dao
interface QuestionTypeDAO {
    @Query("SELECT * FROM ${Constant.DB.TABLES.QUESTION_TYPE}")
    fun getAll(): Single<List<QuestionTypeEntity>>
}