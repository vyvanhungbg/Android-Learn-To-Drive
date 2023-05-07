package com.suspend.android.learntodrive.utils.util

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.suspend.android.learntodrive.model.DayOfWeek
import com.suspend.android.learntodrive.model.Question
import java.util.Date

@ProvidedTypeConverter
class ObjectTypeConverters {
    @TypeConverter
    fun toQuestion(question: String): Question? {
        return Gson().fromJson(question, Question::class.java)
    }

    @TypeConverter
    fun fromQuestion(question: Question): String? {
        return Gson().toJson(question)
    }

    @TypeConverter
    fun toQuestions(question: String): List<Question>? {
        val list = object : TypeToken<List<Question>>() {}.type
        return Gson().fromJson(question, list)
    }

    @TypeConverter
    fun fromQuestions(questions: List<Question>?): String? {
        return Gson().toJson(questions)
    }

    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDayOfWeek(dayOfWeek: String): List<DayOfWeek> {
        val list = object : TypeToken<List<DayOfWeek>>() {}.type
        return Gson().fromJson(dayOfWeek, list)
    }

    @TypeConverter
    fun fromDayOfWeek(dayOfWeekList: List<DayOfWeek>): String {
        return Gson().toJson(dayOfWeekList)
    }

}