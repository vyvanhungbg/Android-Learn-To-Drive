package com.suspend.android.learntodrive.utils.extension

import android.content.SharedPreferences
import com.suspend.android.learntodrive.model.QuestionEntity
import com.suspend.android.learntodrive.model.getTypeQuestionsByNameLicenseType
import com.suspend.android.learntodrive.utils.constant.Constant

fun SharedPreferences.setVoiceSpeechSettings(checked: Boolean) {
    this.edit().putBoolean(Constant.SHAREDPREF.SETTINGS.VOICE, checked).apply()
}

fun SharedPreferences.getVoiceSpeechSettings(): Boolean {
    return this.getBoolean(Constant.SHAREDPREF.SETTINGS.VOICE, false)
}

fun SharedPreferences.setAutoNextSpeechSettings(checked: Boolean) {
    this.edit().putBoolean(Constant.SHAREDPREF.SETTINGS.AUTO_NEXT, checked).apply()
}

fun SharedPreferences.getAutoNextSpeechSettings(): Boolean {
    return this.getBoolean(Constant.SHAREDPREF.SETTINGS.AUTO_NEXT, false)
}

fun SharedPreferences.getCurrentLicenseType(): QuestionEntity.Companion.TYPE {
    val questionCurrentInSetting = this.getString(Constant.SHAREDPREF.SETTINGS.LICENSE_TYPE, "A1")
    //logError(message = getTypeQuestionsByNameLicenseType(questionCurrentInSetting ?: "A1").value)
    return getTypeQuestionsByNameLicenseType(questionCurrentInSetting ?: "A1")
}

fun SharedPreferences.getCurrentLicenseName(): String {
    val questionCurrentInSetting = this.getString(Constant.SHAREDPREF.SETTINGS.LICENSE_TYPE, "A1") ?: "A1"
    //logError(message = questionCurrentInSetting)
    return questionCurrentInSetting;
}

fun SharedPreferences.getDarkModeSettings(): Boolean {
    return this.getBoolean(Constant.SHAREDPREF.SETTINGS.DARK_MODE, false)
}
