package com.suspend.android.learntodrive.ui.setting

import android.app.Dialog
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.suspend.android.learntodrive.R
import com.suspend.android.learntodrive.utils.constant.Constant
import com.suspend.android.learntodrive.utils.extension.getDarkModeSettings
import com.suspend.android.learntodrive.utils.extension.logError
import com.suspend.android.learntodrive.utils.extension.navigateSafe
import com.suspend.android.learntodrive.utils.extension.openDialogQuestion
import com.suspend.android.learntodrive.utils.extension.pickDateTime

private const val TAG = "SettingPreferencesFrag"

class SettingPreferencesFragment : PreferenceFragmentCompat() {

    private var sharedPreferencesListener: SharedPreferences.OnSharedPreferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPref, key ->
             if (key.equals(Constant.SHAREDPREF.SETTINGS.DARK_MODE)) {
                 val isDarkMode = sharedPref.getDarkModeSettings()

                 changeTheme(isDarkMode)
             }
        }


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        setOnClickRestoreDefaultSettings()
        setOnClickAlarmSettings()
    }

    private fun changeTheme(nightMode: Boolean) {
        //val nightMode: Int = AppCompatDelegate.getDefaultNightMode()
        if (nightMode.not()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        activity?.let {
            ActivityCompat.recreate(it)
            it.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

    private fun setOnClickRestoreDefaultSettings() {
        val keyRestoreSetting: Preference? =
            findPreference(Constant.SHAREDPREF.SETTINGS.RESTORE_SETTING)

        keyRestoreSetting?.setOnPreferenceClickListener {
            try {
                context?.let {
                    val dialog = Dialog(it)
                    dialog.openDialogQuestion(
                        it.getString(R.string.text_confirm),
                        getString(R.string.text_dialog_restore_setting_default)
                    ) {
                        restoreDefaultSettings()
                    }
                }

            } catch (e: Exception) {
                logError(TAG, e.message)
            }
            true
        }
    }

    private fun setOnClickAlarmSettings() {
        val keyRestoreSetting: Preference? =
            findPreference(Constant.SHAREDPREF.SETTINGS.ALARM)

        keyRestoreSetting?.setOnPreferenceClickListener {
            try {
                /*context?.let {
                    it.pickDateTime{hour, minute ->
                        Log.e(TAG, "setOnClickAlarmSettings: ${hour}::${minute}", )
                    }
                }*/
                findNavController().navigateSafe(R.id.action_navigation_setting_to_navigation_alarm_fragment)

            } catch (e: Exception) {
                logError(TAG, e.message)
            }
            true
        }
    }

    private fun restoreDefaultSettings() {
        context?.let {
            val preferences: SharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(it)
            val editor = preferences.edit()
            editor.clear()
            editor.apply()
            PreferenceManager.setDefaultValues(it, R.xml.preferences, true)
            preferenceScreen.removeAll()
            onCreatePreferences(null, null) //or onCreate(null) in your code
        }

    }

    override fun onStart() {
        context?.let {
            PreferenceManager.getDefaultSharedPreferences(it)
                .registerOnSharedPreferenceChangeListener(sharedPreferencesListener)
        }
        super.onStart()
    }

    override fun onStop() {
        context?.let {
            PreferenceManager.getDefaultSharedPreferences(it)
                .unregisterOnSharedPreferenceChangeListener(sharedPreferencesListener)
        }
        super.onStop()
    }
}