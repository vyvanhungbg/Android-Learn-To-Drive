package com.suspend.android.learntodrive.ui.alarm.add_alarm

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.suspend.android.learntodrive.R
import com.suspend.android.learntodrive.base.BaseViewModel
import com.suspend.android.learntodrive.data.repository.alarm.IAlarmRepository
import com.suspend.android.learntodrive.model.AlarmEntity

import com.suspend.android.learntodrive.utils.extension.logError
import com.suspend.android.learntodrive.utils.util.DateTimeUtils
import com.suspend.android.learntodrive.utils.util.SingleLiveEvent

private const val TAG = "AddAlarmViewModel"

class AddAlarmViewModel(private val alarmRepository: IAlarmRepository) : BaseViewModel() {

    private val _alarms = MutableLiveData<List<AlarmEntity>>()
    val alarms: LiveData<List<AlarmEntity>> get() = _alarms


    private val _stateAddAlarm = SingleLiveEvent<String>()
    val stateAddAlarm: LiveData<String> get() = _stateAddAlarm


    fun getAlarms() {
        registerDisposable(
            executeTask(
                task = {
                    alarmRepository.getAll()
                },
                onSuccess = {
                    _alarms.value = it
                },
                onError = {
                    _alarms.value = mutableListOf()
                    logError(TAG, it.message)
                }
            )
        )
    }

    fun addAlarm(context: Context, item: AlarmEntity) {
        registerDisposable(
            executeTask(
                task = {
                    alarmRepository.insert(item)
                },
                onSuccess = {
                    _stateAddAlarm.value = context.getString(
                        R.string.mess_add_success_alarm,
                        DateTimeUtils.formatTime(item.hour, item.minute)
                    )
                },
                onError = {
                    // _alarms.value = mutableListOf()
                    _stateAddAlarm.value = context.getString(R.string.mess_add_alarm_failed)
                    logError(TAG, it.message)
                }
            )
        )
    }
}