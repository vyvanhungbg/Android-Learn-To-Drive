package com.suspend.android.learntodrive.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Test(
    val name: String,
    val minimumQuestion: Int,
    val questions: List<Question>,
    val time: Int,
    val indexExam:Int? = null
) : Parcelable