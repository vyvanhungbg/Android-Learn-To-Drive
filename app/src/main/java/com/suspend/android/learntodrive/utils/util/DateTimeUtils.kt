package com.suspend.android.learntodrive.utils.util

object DateTimeUtils {

    fun formatTime(hour: Int, minute: Int): String {
        return "%02d:%02d".format(hour, minute)
    }
}