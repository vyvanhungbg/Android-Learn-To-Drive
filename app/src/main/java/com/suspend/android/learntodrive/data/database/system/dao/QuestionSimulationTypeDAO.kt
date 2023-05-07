package com.suspend.android.learntodrive.data.database.system.dao

import androidx.room.Dao
import androidx.room.Query
import com.suspend.android.learntodrive.model.QuestionSimulationTypeEntity
import com.suspend.android.learntodrive.utils.constant.Constant
import io.reactivex.rxjava3.core.Single

@Dao
interface QuestionSimulationTypeDAO {

    @Query("SELECT * FROM ${Constant.DB.TABLES.QUESTION_SIMULATION_TYPE}")
    fun getAll(): Single<List<QuestionSimulationTypeEntity>>
}