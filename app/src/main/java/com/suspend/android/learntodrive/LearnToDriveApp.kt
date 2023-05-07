package com.suspend.android.learntodrive

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import com.suspend.android.learntodrive.di.SHARED_PREFERENCES_TYPE
import com.suspend.android.learntodrive.di.dataSourceModule
import com.suspend.android.learntodrive.di.databaseModule
import com.suspend.android.learntodrive.di.repositoryModule
import com.suspend.android.learntodrive.di.sharedPreferencesModule
import com.suspend.android.learntodrive.di.textToSpeechModule
import com.suspend.android.learntodrive.di.viewModelModule
import com.suspend.android.learntodrive.utils.extension.getDarkModeSettings
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named

const val CHANNEL_ID = "com.suspend.android.learntodrive"
const val CHANNEL_NAME = "channel_learn_to_drive"
class LearnToDriveApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@LearnToDriveApp)
            modules(
                dataSourceModule,
                repositoryModule,
                viewModelModule,
                databaseModule,
                textToSpeechModule,
                sharedPreferencesModule
            )
        }
        val sharedPreferencesModule = get<SharedPreferences>(named(name = SHARED_PREFERENCES_TYPE.SETTINGS))
        val isDarkModeTheme = sharedPreferencesModule.getDarkModeSettings()
        changeTheme(isDarkModeTheme)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }

    private fun changeTheme(nightMode: Boolean) {
        //val nightMode: Int = AppCompatDelegate.getDefaultNightMode()
        if (nightMode.not()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}