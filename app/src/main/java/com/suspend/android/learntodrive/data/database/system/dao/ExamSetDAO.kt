package com.suspend.android.learntodrive.data.database.system.dao

import androidx.room.Dao
import androidx.room.Query
import com.suspend.android.learntodrive.model.ExamSetEntity
import com.suspend.android.learntodrive.utils.constant.Constant
import io.reactivex.rxjava3.core.Single

@Dao
interface ExamSetDAO {

    @Query("SELECT * FROM ${Constant.DB.TABLES.EXAM_SET} WHERE :id=id_license")
    fun getExamTestByIDLicense(id: Int): Single<List<ExamSetEntity>>

    @Query("SELECT * FROM ${Constant.DB.TABLES.EXAM_SET} WHERE :idLicense=id_license AND :indexExam=index_exam")
    fun getExamSetByIdLicenseAndIndexExam(
        idLicense: Int,
        indexExam: Int
    ): Single<List<ExamSetEntity>>
}