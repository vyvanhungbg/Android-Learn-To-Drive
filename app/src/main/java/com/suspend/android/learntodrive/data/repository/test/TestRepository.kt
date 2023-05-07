package com.suspend.android.learntodrive.data.repository.test

import com.suspend.android.learntodrive.data.datasource.test.ITestDataSource
import com.suspend.android.learntodrive.model.TestEntity
import io.reactivex.rxjava3.core.Single

class TestRepository(private val local: ITestDataSource.Local) : ITestRepository {
    override fun getAll(type: String): Single<List<TestEntity>> {
        return local.getAll(type)
    }

    override fun insert(test: TestEntity): Single<Long> {
        return local.insert(test)
    }

    override fun getByID(id: Int): Single<TestEntity> {
        return local.getByID(id)
    }

    override fun getByRowID(rowId: Long): Single<TestEntity> {
        return local.getByRowID(rowId)
    }

    override fun getTestByTypeAndIndexExam(
        nameLicense: String,
        indexExamSet: Int
    ): Single<List<TestEntity>> {
        return local.getTestByTypeAndIndexExam(nameLicense, indexExamSet)
    }
}
