package com.suspend.android.learntodrive.data.datasource.exam_set

import com.suspend.android.learntodrive.data.database.system.DatabaseLocal
import com.suspend.android.learntodrive.model.ExamSetEntity
import io.reactivex.rxjava3.core.Single

class ExamSetLocalDataSource(private val databaseLocal: DatabaseLocal) : IExamSetDataSource.Local {

    override fun getExamTestByIDLicense(id: Int): Single<List<ExamSetEntity>> {
        return databaseLocal.examSetDAO.getExamTestByIDLicense(id)
    }

    override fun getExamSetByIdLicenseAndIndexExam(
        idLicense: Int,
        indexExam: Int
    ): Single<List<ExamSetEntity>> {
        return databaseLocal.examSetDAO.getExamSetByIdLicenseAndIndexExam(idLicense, indexExam)
    }
}