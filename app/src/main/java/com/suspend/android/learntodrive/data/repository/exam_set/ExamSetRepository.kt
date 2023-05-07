package com.suspend.android.learntodrive.data.repository.exam_set

import com.suspend.android.learntodrive.data.datasource.exam_set.IExamSetDataSource
import com.suspend.android.learntodrive.model.ExamSetEntity
import com.suspend.android.learntodrive.model.LicenseType
import io.reactivex.rxjava3.core.Single

class ExamSetRepository(private val local: IExamSetDataSource.Local) : IExamSetRepository {

    override fun getExamTestByIDLicense(nameLicense: String): Single<List<ExamSetEntity>> {
        return when (nameLicense) {
            LicenseType.A1.name -> local.getExamTestByIDLicense(1)
            LicenseType.A2.name -> local.getExamTestByIDLicense(2)
            LicenseType.A3.name, LicenseType.A4.name -> local.getExamTestByIDLicense(3)
            LicenseType.B1.name -> local.getExamTestByIDLicense(5)
            LicenseType.B2.name -> local.getExamTestByIDLicense(6)
            LicenseType.C.name -> local.getExamTestByIDLicense(7)
            LicenseType.D.name, LicenseType.E.name, LicenseType.F.name -> local.getExamTestByIDLicense(
                8
            )
            else -> local.getExamTestByIDLicense(1)
        }
    }

    override fun getExamSetByIdLicenseAndIndexExam(
        nameLicense: String,
        indexExam: Int
    ): Single<List<ExamSetEntity>> {
        return when (nameLicense) {
            LicenseType.A1.name -> local.getExamSetByIdLicenseAndIndexExam(
                idLicense = 1,
                indexExam = indexExam
            )
            LicenseType.A2.name -> local.getExamSetByIdLicenseAndIndexExam(
                idLicense = 2,
                indexExam = indexExam
            )
            LicenseType.A3.name, LicenseType.A4.name -> local.getExamSetByIdLicenseAndIndexExam(
                idLicense = 3,
                indexExam = indexExam
            )
            LicenseType.B1.name -> local.getExamSetByIdLicenseAndIndexExam(
                idLicense = 5,
                indexExam = indexExam
            )
            LicenseType.B2.name -> local.getExamSetByIdLicenseAndIndexExam(
                idLicense = 6,
                indexExam = indexExam
            )
            LicenseType.C.name -> local.getExamSetByIdLicenseAndIndexExam(
                idLicense = 7,
                indexExam = indexExam
            )
            LicenseType.D.name, LicenseType.E.name, LicenseType.F.name -> local.getExamSetByIdLicenseAndIndexExam(
                idLicense = 8,
                indexExam = indexExam
            )
            else -> local.getExamSetByIdLicenseAndIndexExam(idLicense = 1, indexExam = indexExam)
        }
    }
}