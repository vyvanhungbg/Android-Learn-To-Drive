package com.suspend.android.learntodrive.ui.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.suspend.android.learntodrive.base.BaseViewModel
import com.suspend.android.learntodrive.data.repository.alarm.IAlarmRepository
import com.suspend.android.learntodrive.model.AlarmEntity
import com.suspend.android.learntodrive.utils.extension.logError

private const val TAG = "AlarmViewModel"

class AlarmViewModel(private val alarmRepository: IAlarmRepository) : BaseViewModel() {

    private val _alarms = MutableLiveData<List<AlarmEntity>>()
    val alarms: LiveData<List<AlarmEntity>> get() = _alarms


    fun getAlarms() {
        registerDisposable(
            executeTask(
                task = {
                    alarmRepository.getAll()
                },
                onSuccess = {
                    _alarms.value = it
                    logError(TAG, it.toString())
                },
                onError = {
                    _alarms.value = mutableListOf()
                    logError(TAG, it.message)
                }
            )
        )
    }

    fun delete(context: Context, alarm: AlarmEntity) {
        destroyAlarms(context, alarm)
        registerDisposable(
            executeTask(
                task = {
                    alarmRepository.deleteByID(alarm.id)
                },
                onSuccess = {
                    // _alarms.value = it
                    getAlarms()
                },
                onError = {
                    //_alarms.value = mutableListOf()
                    logError(TAG, it.message)
                }
            )
        )
    }

    fun destroyAlarms(context: Context, alarm: AlarmEntity) {
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            alarm.id,
            Intent(),
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager =
            ContextCompat.getSystemService(context, AlarmManager::class.java)

        alarmManager?.cancel(pendingIntent)
    }

    fun update(alarm: AlarmEntity) {
        // update không cần sửa lại alarm vì đã check enabled khi bật thông báo
        registerDisposable(
            executeTask(
                task = {
                    alarmRepository.update(alarm)
                },
                onSuccess = {
                    // _alarms.value = it
                    getAlarms()
                },
                onError = {
                    //_alarms.value = mutableListOf()
                    logError(TAG, it.message)
                }
            )
        )
    }

    override fun onCleared() {
        super.onCleared()
        logError(TAG, "onCleared alarm")
    }
}