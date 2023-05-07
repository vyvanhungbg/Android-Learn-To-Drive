package com.suspend.android.learntodrive.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.suspend.android.learntodrive.utils.constant.Constant

@Entity(tableName = Constant.DB.TABLES.TIPS)
data class TipEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val type: Int?,
    val content: String?
)
