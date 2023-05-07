package com.suspend.android.learntodrive.data.repository.test

import com.suspend.android.learntodrive.model.TestEntity
import io.reactivex.rxjava3.core.Single

interface ITestRepository {
    fun getAll(type: String): Single<List<TestEntity>>
    fun insert(test: TestEntity): Single<Long>
    fun getByID(id: Int): Single<TestEntity>
    fun getByRowID(rowId: Long): Single<TestEntity>
    fun getTestByTypeAndIndexExam(nameLicense: String, indexExamSet: Int): Single<List<TestEntity>>
}