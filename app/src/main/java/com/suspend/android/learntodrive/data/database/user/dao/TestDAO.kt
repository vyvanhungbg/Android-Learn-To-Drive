package com.suspend.android.learntodrive.data.database.user.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

import androidx.room.Query
import com.suspend.android.learntodrive.model.TestEntity
import com.suspend.android.learntodrive.utils.constant.Constant
import io.reactivex.rxjava3.core.Single

@Dao
interface TestDAO {

    @Query("SELECT * FROM ${Constant.DB_USER.TABLES.TEST} WHERE type=:type  ORDER BY createdAt DESC ")
    fun getAll(type: String): Single<List<TestEntity>>

    @Query("SELECT * FROM ${Constant.DB_USER.TABLES.TEST} WHERE id=:id")
    fun getByID(id: Int): Single<TestEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: TestEntity): Single<Long>

    @Query("SELECT * FROM ${Constant.DB_USER.TABLES.TEST} WHERE rowid = :rowId LIMIT 1")
    fun getByRowID(rowId: Long): Single<TestEntity>

    @Query("SELECT * FROM ${Constant.DB_USER.TABLES.TEST} WHERE :nameLicense = type AND :indexExamSet = indexExam")
    fun getTestByTypeAndIndexExam(nameLicense: String, indexExamSet: Int): Single<List<TestEntity>>
}