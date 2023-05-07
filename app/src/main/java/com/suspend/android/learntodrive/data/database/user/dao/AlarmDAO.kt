package com.suspend.android.learntodrive.data.database.user.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.suspend.android.learntodrive.model.AlarmEntity
import com.suspend.android.learntodrive.model.DayOfWeek
import com.suspend.android.learntodrive.utils.constant.Constant
import io.reactivex.rxjava3.core.Single

@Dao
interface AlarmDAO {

    @Query("SELECT * FROM ${Constant.DB_USER.TABLES.ALARM}  ORDER BY id DESC ")
    fun getAll(): Single<List<AlarmEntity>>

    @Query("SELECT * FROM ${Constant.DB_USER.TABLES.ALARM} WHERE id=:id LIMIT 1")
    fun getByID(id: Int): Single<AlarmEntity>

    @Query("SELECT * FROM ${Constant.DB_USER.TABLES.ALARM} WHERE :dayOfWeek in (dayRedmine) ")
    fun getByDayRedmine(dayOfWeek: DayOfWeek): Single<List<AlarmEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: AlarmEntity): Single<Long>

    @Update
    fun update(entity: AlarmEntity): Single<Int>

    @Query("DELETE FROM ${Constant.DB_USER.TABLES.ALARM} WHERE id=:id ")
    fun deleteByID(id: Int): Single<Int>

    /* @Query("DELETE FROM ${Constant.DB_USER.TABLES.ALARM} WHERE requestCode= :requestCode")
     fun deleteByRequestCode(requestCode: Int): Single<Int>*/
}