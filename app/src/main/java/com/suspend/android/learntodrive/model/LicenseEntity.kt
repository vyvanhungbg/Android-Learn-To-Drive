package com.suspend.android.learntodrive.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.suspend.android.learntodrive.utils.constant.Constant
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = Constant.DB.TABLES.LICENSE)
data class LicenseEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "number_of_question")
    val numberOfQuestion: Int,
    @ColumnInfo(name = "number_of_required_question")
    val numberOfRequiredQuestion: Int,
    @ColumnInfo(name = "number_of_question_per_test")
    val numberOfQuestionPerTest: Int,
    @ColumnInfo(name = "number_of_correct_question_per_test")
    val numberOfCorrectQuestionPerTest: Int,
    @ColumnInfo(name = "duration")
    val duration: Int,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "image")
    val image: String?,
    @ColumnInfo(name = "content")
    val content: String?,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "effective_time")
    val effectiveTime: String?,
    @ColumnInfo(name = "exam_conditions")
    val examConditions: String?,
) : Parcelable