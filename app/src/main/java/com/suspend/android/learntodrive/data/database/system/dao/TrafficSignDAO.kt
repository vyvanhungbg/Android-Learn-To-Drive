package com.suspend.android.learntodrive.data.database.system.dao

import androidx.room.Dao
import androidx.room.Query
import com.suspend.android.learntodrive.model.TrafficSignEntity
import com.suspend.android.learntodrive.model.TrafficSignTypeEntity
import com.suspend.android.learntodrive.utils.constant.Constant
import io.reactivex.rxjava3.core.Single

@Dao
interface TrafficSignDAO {
    @Query("SELECT * FROM ${Constant.DB.TABLES.SIGN_TYPE}")
    fun getAllTypeTrafficSigns(): Single<List<TrafficSignTypeEntity>>

    @Query("SELECT * FROM ${Constant.DB.TABLES.SIGNS}")
    fun getAllTrafficSigns(): Single<List<TrafficSignEntity>>
}