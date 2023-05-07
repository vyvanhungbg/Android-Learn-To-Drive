package com.suspend.android.learntodrive.di

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.suspend.android.learntodrive.utils.constant.Constant
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module

object SHARED_PREFERENCES_TYPE {
    const val SETTINGS = "settings_shared_pref"
    const val NORMAL = "settings_shared_normal"
}

val sharedPreferencesModule = module {
    single(named(SHARED_PREFERENCES_TYPE.NORMAL)) {
        provideSharedPrefs(androidApplication())
    }

    single(named(SHARED_PREFERENCES_TYPE.SETTINGS)) {
        provideSharedPrefsSettings(androidApplication())
    }

    single<SharedPreferences.Editor> {
        provideSharedPrefs(androidApplication()).edit()
    }
}

fun provideSharedPrefs(androidApplication: Application): SharedPreferences {
    return androidApplication.getSharedPreferences(
        Constant.SHAREDPREF.ROOT,
        android.content.Context.MODE_PRIVATE
    )
}

fun provideSharedPrefsSettings(androidApplication: Application): SharedPreferences? {
    return PreferenceManager.getDefaultSharedPreferences(androidApplication)
}
