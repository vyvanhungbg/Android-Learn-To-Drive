package com.suspend.android.learntodrive.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.suspend.android.learntodrive.utils.constant.Constant
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = Constant.DB.TABLES.SIGN_TYPE)
class TrafficSignTypeEntity(
    @PrimaryKey
    val id: Int,
    val name: String
) : Parcelable