package com.suspend.android.learntodrive.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.suspend.android.learntodrive.utils.constant.Constant

@Entity(tableName = Constant.DB.TABLES.QUESTIONS)
data class QuestionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val content: String?,
    val option1: String?,
    val option2: String?,
    val option3: String?,
    val option4: String?,
    val image: String?,
    val answer: Int?,
    @ColumnInfo(name = "question_type")
    val questionType: Int?,
    val type200: Int?,
    val type450: Int?,
    val type500: Int?,
    val type574: Int?,
    val type600: Int?,
    @ColumnInfo(name = "question_die")
    val questionDie: Int?,
    val description: String?
) {
    companion object {
        enum class TYPE(val value: String) {
            TYPE_200("type200"),
            TYPE_450("type450"),
            TYPE_500("type500"),
            TYPE_574("type570"),
            TYPE_600("type600"),
        }
    }

    fun isQuestionDie(): Boolean {
        return if (questionDie == null) {
            false
        } else {
            questionDie > 0
        }
    }
}

fun getTypeQuestionsByNameLicenseType(nameType: String = LicenseType.A1.name) = when (nameType) {
    LicenseType.A1.name -> QuestionEntity.Companion.TYPE.TYPE_200
    LicenseType.A2.name -> QuestionEntity.Companion.TYPE.TYPE_450
    LicenseType.A3.name, LicenseType.A4.name -> QuestionEntity.Companion.TYPE.TYPE_500
    LicenseType.B1.name -> QuestionEntity.Companion.TYPE.TYPE_574
    LicenseType.B2.name, LicenseType.C.name, LicenseType.D.name, LicenseType.E.name, LicenseType.F.name -> QuestionEntity.Companion.TYPE.TYPE_600
    else -> QuestionEntity.Companion.TYPE.TYPE_200
}

fun QuestionEntity.toQuestion(): Question {
    var position = 1
    val answerString = mutableListOf<String?>(option1, option2, option3, option4)
    val answerList = answerString.filter { !it.isNullOrEmpty() }
        .map { Answer(position++, it, isCorrectQuestion = position.dec() == this.answer) }
    val _questionDie = if (questionDie != null) questionDie == 1 else false
    return Question(
        id,
        content,
        answerList,
        image,
        answer,
        answerSelected = 0,
        _questionDie,
        description
    )
}
