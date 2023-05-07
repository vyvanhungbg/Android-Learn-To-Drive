package com.suspend.android.learntodrive.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.suspend.android.learntodrive.utils.constant.Constant
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = Constant.DB_USER.TABLES.ALARM)
data class AlarmEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val description: String?,
    val title: String?,
    val dayRedmine: List<DayOfWeek>,
    val hour: Int = 0,
    val minute: Int = 0,
    val enabled: Boolean = false
) : Parcelable {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<AlarmEntity>() {
            override fun areItemsTheSame(
                oldItem: AlarmEntity,
                newItem: AlarmEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: AlarmEntity,
                newItem: AlarmEntity
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}
