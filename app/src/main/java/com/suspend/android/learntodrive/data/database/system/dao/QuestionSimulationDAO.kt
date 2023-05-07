package com.suspend.android.learntodrive.data.database.system.dao

import androidx.room.Dao
import androidx.room.Query
import com.suspend.android.learntodrive.model.QuestionSimulationEntity
import com.suspend.android.learntodrive.utils.constant.Constant
import io.reactivex.rxjava3.core.Single

@Dao
interface QuestionSimulationDAO {

    @Query("SELECT * FROM ${Constant.DB.TABLES.QUESTION_SIMULATION}")
    fun getAll(): Single<List<QuestionSimulationEntity>>

    @Query("SELECT * FROM ${Constant.DB.TABLES.QUESTION_SIMULATION} WHERE :id = id LIMIT 1")
    fun getQuestionSimulationByID(id: Int): Single<QuestionSimulationEntity>

    @Query("SELECT * FROM ${Constant.DB.TABLES.QUESTION_SIMULATION} WHERE :type = simulation_type")
    fun getQuestionSimulationByType(type: Int): Single<List<QuestionSimulationEntity>>
}