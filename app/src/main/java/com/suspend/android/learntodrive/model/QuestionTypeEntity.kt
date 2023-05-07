package com.suspend.android.learntodrive.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.suspend.android.learntodrive.utils.constant.Constant
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = Constant.DB.TABLES.QUESTION_TYPE)
data class QuestionTypeEntity(
    @PrimaryKey
    val id: Int,
    val description: String?,
    val image: String?,
    val name: String?,
    val typeA1: Int?,
    val typeA2: Int?,
    val typeA3: Int?,
    val typeA4: Int?,
    val typeB1: Int?,
    val typeB2: Int?,
    val typeC: Int?,
    val typeD: Int?,
    val typeE: Int?,
    val typeF: Int?,
) : Parcelable {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<QuestionTypeEntity>() {
            override fun areItemsTheSame(oldItem: QuestionTypeEntity, newItem: QuestionTypeEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: QuestionTypeEntity, newItem: QuestionTypeEntity): Boolean {
                return oldItem == newItem
            }

        }
    }
}