package com.suspend.android.learntodrive.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.suspend.android.learntodrive.utils.constant.Constant
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = Constant.DB.TABLES.SIGNS)
data class TrafficSignEntity(
    @PrimaryKey
    val id: Int,
    val type: Int,
    val image: String,
    val name: String,
    val description: String?,
    val path: String
) : Parcelable {
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<TrafficSignEntity>() {
            override fun areItemsTheSame(
                oldItem: TrafficSignEntity,
                newItem: TrafficSignEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: TrafficSignEntity,
                newItem: TrafficSignEntity
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}