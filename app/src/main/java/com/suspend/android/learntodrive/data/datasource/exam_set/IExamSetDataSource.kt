package com.suspend.android.learntodrive.data.datasource.exam_set

import com.suspend.android.learntodrive.model.ExamSetEntity
import com.suspend.android.learntodrive.model.LicenseEntity
import io.reactivex.rxjava3.core.Single

interface IExamSetDataSource {

    interface Local{
        fun getExamTestByIDLicense(id: Int): Single<List<ExamSetEntity>>

        fun getExamSetByIdLicenseAndIndexExam(
            idLicense: Int,
            indexExam: Int
        ): Single<List<ExamSetEntity>>
    }
}