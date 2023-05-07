package com.suspend.android.learntodrive.model

import androidx.recyclerview.widget.DiffUtil
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.suspend.android.learntodrive.utils.constant.Constant

@Entity(tableName = Constant.DB.TABLES.QUESTION_SIMULATION_TYPE)
data class QuestionSimulationTypeEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String?,
    val image: String?,
    @ColumnInfo(name = "number_of_question")
    val numberOfQuestion: Int
) {
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<QuestionSimulationTypeEntity>() {
            override fun areItemsTheSame(
                oldItem: QuestionSimulationTypeEntity,
                newItem: QuestionSimulationTypeEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: QuestionSimulationTypeEntity,
                newItem: QuestionSimulationTypeEntity
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}