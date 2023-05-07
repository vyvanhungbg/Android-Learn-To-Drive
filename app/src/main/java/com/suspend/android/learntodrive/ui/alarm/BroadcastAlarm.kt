package com.suspend.android.learntodrive.ui.alarm


import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.suspend.android.learntodrive.CHANNEL_ID
import com.suspend.android.learntodrive.R
import com.suspend.android.learntodrive.data.datasource.alarm.AlarmLocalDataSource
import com.suspend.android.learntodrive.data.datasource.tips.TipLocalDataSource
import com.suspend.android.learntodrive.data.repository.alarm.AlarmRepository
import com.suspend.android.learntodrive.data.repository.alarm.IAlarmRepository
import com.suspend.android.learntodrive.data.repository.tips.ITipRepository
import com.suspend.android.learntodrive.data.repository.tips.TipRepository
import com.suspend.android.learntodrive.di.provideDatabase
import com.suspend.android.learntodrive.di.provideDatabaseUser
import com.suspend.android.learntodrive.ui.main.MainActivity
import com.suspend.android.learntodrive.utils.constant.Constant
import com.suspend.android.learntodrive.utils.extension.logError
import com.suspend.android.learntodrive.utils.extension.withIOToMainThread
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.util.Date

private const val TAG = "BroadcastAlarm"

class BroadcastAlarm : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent?) {

        try {
            val alarmID = intent?.getIntExtra(Constant.ALARM.BUNDLE_ALARM_ENTITY_ID, -1) ?: -1

            if (alarmID != -1) {
                val databaseUser = provideDatabaseUser(context)
                val databaseQuestion = provideDatabase(context)
                val alarmRepository: IAlarmRepository =
                    AlarmRepository(AlarmLocalDataSource(databaseUser))

                val tipRepository: ITipRepository =
                    TipRepository(TipLocalDataSource(databaseQuestion))


                executeTask(
                    task = { tipRepository.getAll() },

                    onSuccess = {
                        val tipRandom = it.filter { item -> item.content != null }.randomOrNull()
                        executeTask(task = { alarmRepository.getByID(alarmID) },
                            onSuccess = { _alarm ->
                                val title =
                                    _alarm.title
                                        ?: context.getString(R.string.mess_title_notification)
                                var content =
                                    context.getString(R.string.mess_tip_content, tipRandom?.content)
                                if (tipRandom?.content == null) {
                                    content = context.getString(R.string.mess_content_notification)
                                }

                                if (_alarm.enabled) {
                                    createNotification(context, title, content)
                                } else {
                                    logError(TAG, "Đã tắt nhắc nhở có id ${alarmID}")
                                }
                            }, onError = {
                                logError(TAG, it.message)
                            })
                    },
                    onError = {
                        logError(TAG, it.message)
                    })

            }
        } catch (e: Exception) {
            logError(TAG, e.message)
        }

    }


    private fun <T : Any> executeTask(
        task: () -> Single<T>,
        onSuccess: (T) -> Unit,
        onError: (Throwable) -> Unit,

        ) {
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
            task().withIOToMainThread().doFinally {
                compositeDisposable.clear()
            }.subscribe({ onSuccess(it) }, { onError(it) })
        )
    }

    private fun createNotification(context: Context, title: String, content: String) {

        val soundUri =
            Uri.parse("android.resource://" + context.packageName + "/" + R.raw.sound_notification)

        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_logo)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(soundUri)
            .setVibrate(longArrayOf(0, 200, 200, 500))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(Date().time.toInt(), builder.build())
    }
}