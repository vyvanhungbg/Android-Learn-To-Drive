package com.suspend.android.learntodrive.data.datasource.test

import com.suspend.android.learntodrive.data.database.user.DatabaseUser
import com.suspend.android.learntodrive.model.TestEntity
import io.reactivex.rxjava3.core.Single

class TestDataSource(private val database: DatabaseUser) : ITestDataSource.Local {
    override fun getAll(type: String): Single<List<TestEntity>> {
        return database.testDao.getAll(type)
    }

    override fun insert(test: TestEntity): Single<Long> {
        return database.testDao.insert(test)
    }

    override fun getByID(id: Int): Single<TestEntity> {
        return database.testDao.getByID(id)
    }

    override fun getByRowID(rowId: Long): Single<TestEntity> {
        return database.testDao.getByRowID(rowId)
    }

    override fun getTestByTypeAndIndexExam(
        nameLicense: String,
        indexExamSet: Int
    ): Single<List<TestEntity>> {
        return database.testDao.getTestByTypeAndIndexExam(nameLicense, indexExamSet)
    }
}