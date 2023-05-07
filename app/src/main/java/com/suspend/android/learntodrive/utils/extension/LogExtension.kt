package com.suspend.android.learntodrive.utils.extension

import android.util.Log
import com.suspend.android.learntodrive.BuildConfig

const val defaultMessage = ""

fun logError(TAG: String = "Error : ", message: String?) {
    if (BuildConfig.DEBUG) {
        Log.e(TAG, message ?: defaultMessage)
    }
}

fun logInfo(TAG: String = "Info : ", message: String?) {
    if (BuildConfig.DEBUG) {
        Log.i(TAG, message ?: defaultMessage)
    }
}

fun logDebug(TAG: String = "Debug : ", message: String?) {
    if (BuildConfig.DEBUG) {
        Log.d(TAG, message ?: defaultMessage)
    }
}

