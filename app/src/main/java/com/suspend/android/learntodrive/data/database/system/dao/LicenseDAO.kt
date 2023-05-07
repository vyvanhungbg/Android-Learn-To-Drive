package com.suspend.android.learntodrive.data.database.system.dao

import androidx.room.Dao
import androidx.room.Query
import com.suspend.android.learntodrive.model.LicenseEntity
import com.suspend.android.learntodrive.utils.constant.Constant
import io.reactivex.rxjava3.core.Single

@Dao
interface LicenseDAO {

    @Query("SELECT * FROM ${Constant.DB.TABLES.LICENSE}")
    fun getAll(): Single<List<LicenseEntity>>

    @Query("SELECT * FROM ${Constant.DB.TABLES.LICENSE} WHERE :name = name")
    fun getLicenseByName(name:String): Single<LicenseEntity>
}