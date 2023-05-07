package com.suspend.android.learntodrive.data.database.system.dao

import androidx.room.Dao
import androidx.room.Query
import com.suspend.android.learntodrive.model.TipEntity
import com.suspend.android.learntodrive.utils.constant.Constant
import io.reactivex.rxjava3.core.Single

@Dao
interface TipDAO {
    @Query("SELECT * FROM ${Constant.DB.TABLES.TIPS}")
    fun getAll(): Single<List<TipEntity>>
}