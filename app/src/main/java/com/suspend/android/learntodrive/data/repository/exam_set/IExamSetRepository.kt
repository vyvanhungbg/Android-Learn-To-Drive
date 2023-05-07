package com.suspend.android.learntodrive.data.repository.exam_set

import com.suspend.android.learntodrive.model.ExamSetEntity
import io.reactivex.rxjava3.core.Single

interface IExamSetRepository {

    fun getExamTestByIDLicense(nameLicense: String): Single<List<ExamSetEntity>>

    fun getExamSetByIdLicenseAndIndexExam(
        nameLicense: String,
        indexExam: Int
    ): Single<List<ExamSetEntity>>
}