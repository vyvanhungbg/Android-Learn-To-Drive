package com.suspend.android.learntodrive.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class ResultTest(
    val correctAnswer: Int = 0,
    val wrongAnswer: Int = 0,
    val ignoreAnswer: Int = 0,
    val timeTest: Long = 0,
    val reason: String? = null,
    val passTest: Boolean = false,
    val test: Test?,
    val indexExam:Int? = null
) : Parcelable

fun ResultTest.toTestEntity(type: String): TestEntity {
    return TestEntity(
        name = test?.name,
        type = type,
        correctAnswer = correctAnswer,
        wrongAnswer = wrongAnswer,
        ignoreAnswer = ignoreAnswer,
        timeTest = timeTest,
        reason = reason,
        passTest = passTest,
        questions = test?.questions,
        createdAt = Date(),
        indexExam = indexExam,
    )
}
