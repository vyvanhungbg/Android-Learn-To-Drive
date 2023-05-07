package com.suspend.android.learntodrive.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class DayOfWeek(val valueInCalendar: Int) : Parcelable {
    MONDAY(2),
    TUESDAY(3),
    WEDNESDAY(4),
    THURSDAY(5),
    FRIDAY(6),
    SATURDAY(7),
    SUNDAY(1)
}

fun DayOfWeek.convertToString(): String {
    if (this == DayOfWeek.SUNDAY)
        return "CN"
    else
        return "T${this.valueInCalendar}"
}