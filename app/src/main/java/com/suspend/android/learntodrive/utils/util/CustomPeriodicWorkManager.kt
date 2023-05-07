package com.suspend.android.learntodrive.utils.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.suspend.android.learntodrive.data.datasource.alarm.AlarmLocalDataSource
import com.suspend.android.learntodrive.data.repository.alarm.AlarmRepository
import com.suspend.android.learntodrive.data.repository.alarm.IAlarmRepository
import com.suspend.android.learntodrive.di.provideDatabaseUser
import com.suspend.android.learntodrive.model.AlarmEntity
import com.suspend.android.learntodrive.model.DayOfWeek
import com.suspend.android.learntodrive.ui.alarm.BroadcastAlarm
import com.suspend.android.learntodrive.utils.constant.Constant
import com.suspend.android.learntodrive.utils.extension.logError
import java.util.Calendar

private const val TAG = "PeriodicWorkManager"

class CustomPeriodicWorkManager(
    private val context: Context,
    private val parameters: WorkerParameters
) : Worker(context, parameters) {


    private val alarmManager =
        ContextCompat.getSystemService(context, AlarmManager::class.java)

    override fun doWork(): Result {
        /* val time = parameters.inputData.keyValueMap["time"] as? String
         val intent = Intent(context, BroadcastAlarm::class.java)
         intent.action = Constant.ALARM.INTENT_FILTER
         intent.putExtra("time", time)
         context.sendBroadcast(intent)*/

        try {
            val databaseUser = provideDatabaseUser(context)
            val alarmRepository: IAlarmRepository =
                AlarmRepository(AlarmLocalDataSource(databaseUser))
            val alarmList = alarmRepository.getAll().blockingGet()

            // lọc lấy ngày đang bật và ngày hiện tại
            val alarmEnabled =
                alarmList.filter { it.enabled && it.dayRedmine.contains(getDayOfWeekToDay()) }


            alarmEnabled.forEach { _alarm ->
                createAlarm(context, _alarm, getDayOfWeekToDay().valueInCalendar)
            }
            return Result.success()
        } catch (ex: Exception) {
            logError(TAG, ex.message)
            return Result.failure()
        }

    }

    private fun getDayOfWeekToDay(): DayOfWeek {
        val calendar = Calendar.getInstance()
        val dayOfWeek = calendar[Calendar.DAY_OF_WEEK]
        return DayOfWeek.values().find { it.valueInCalendar == dayOfWeek }!!
    }

    private fun createAlarm(context: Context, alarm: AlarmEntity, dayEnabledRedmine: Int) {

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, alarm.hour) // thiết lập giờ
            set(Calendar.MINUTE, alarm.minute) // thiết lập phút
            set(Calendar.SECOND, 0) // đặt giây bằng 0
            set(Calendar.MILLISECOND, 0) // đặt mili giây bằng 0
        }

        // thiết lập thứ
        calendar.set(Calendar.DAY_OF_WEEK, dayEnabledRedmine)

        val intent = Intent(context, BroadcastAlarm::class.java)
        intent.action = Constant.ALARM.INTENT_FILTER
        intent.putExtra(Constant.ALARM.BUNDLE_ALARM_ENTITY_ID, alarm.id)


        val pendingIntent = PendingIntent.getBroadcast(
            context,
            alarm.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // hủy nếu đã set và tạo lại mới
        // val istimeHasPassed = calendar >=
        alarmManager?.cancel(pendingIntent)
        if (Calendar.getInstance() < calendar) { // thời  gian đã  trôi qua

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager?.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            } else {
                alarmManager?.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            }
        } else {
            logError(TAG, "Đã quá thời gian ngày hôm nay không set alarm")
        }

    }


}