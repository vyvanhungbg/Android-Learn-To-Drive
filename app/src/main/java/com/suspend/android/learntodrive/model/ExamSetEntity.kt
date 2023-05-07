package com.suspend.android.learntodrive.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.suspend.android.learntodrive.utils.constant.Constant
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = Constant.DB.TABLES.EXAM_SET)
data class ExamSetEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "id_license")
    val idLicense: Int,
    @ColumnInfo(name = "index_exam")
    val indexExam: Int,
    @ColumnInfo(name = "id_question")
    val idQuestion: Int,
) : Parcelable {

}