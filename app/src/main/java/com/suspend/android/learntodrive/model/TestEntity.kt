package com.suspend.android.learntodrive.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.suspend.android.learntodrive.utils.constant.Constant
import kotlinx.parcelize.Parcelize

import java.util.Date

@Parcelize
@Entity(tableName = Constant.DB_USER.TABLES.TEST)
data class TestEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String?,
    val type: String, // name of license
    val correctAnswer: Int = 0,
    val wrongAnswer: Int = 0,
    val ignoreAnswer: Int = 0,
    val timeTest: Long = 0,
    val reason: String? = null,
    val passTest: Boolean = false,
    val questions: List<Question>?,
    val createdAt: Date?,
    val indexExam: Int? = null // stt test in test exam set
) : Parcelable {

    fun isTestRandom(): Boolean {
        return indexExam == null
    }
}
