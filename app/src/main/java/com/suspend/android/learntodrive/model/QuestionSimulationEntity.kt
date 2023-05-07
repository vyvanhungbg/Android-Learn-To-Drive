package com.suspend.android.learntodrive.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.suspend.android.learntodrive.utils.constant.Constant

@Entity(tableName = Constant.DB.TABLES.QUESTION_SIMULATION)
data class QuestionSimulationEntity(
    @PrimaryKey
    val id: Int,
    val instruction: String?,
    @ColumnInfo(name = "simulation_type")
    val simulationType: Int,
    val description: String?,
    @ColumnInfo(name = "time_1")
    val time1: Double?,
    @ColumnInfo(name = "time_2")
    val time2: Double?,
    val score: Int,
    @ColumnInfo(name = "start_time")
    val startTime: Int,
    val path: String?
)

fun QuestionSimulationEntity.toVideoSimulation(streamURL: String): VideoSimulation {
    return VideoSimulation(
        id = this.id,
        instruction = this.instruction ?: "",
        simulationType = this.simulationType,
        description = this.description ?: "",
        score = this.score,
        startTime = this.startTime,
        streamURL = streamURL
    )
}