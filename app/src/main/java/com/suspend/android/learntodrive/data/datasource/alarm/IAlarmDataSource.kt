package com.suspend.android.learntodrive.data.datasource.alarm

import com.suspend.android.learntodrive.model.AlarmEntity
import com.suspend.android.learntodrive.model.DayOfWeek
import io.reactivex.rxjava3.core.Single

interface IAlarmDataSource {
    interface Local {
        fun getAll(): Single<List<AlarmEntity>>

        fun getByDayRedmine(dayOfWeek: DayOfWeek): Single<List<AlarmEntity>>

        fun getByID(id: Int): Single<AlarmEntity>

        fun insert(entity: AlarmEntity): Single<Long>

        fun update(entity: AlarmEntity): Single<Int>

        fun deleteByID(id: Int): Single<Int>

       // fun deleteByRequestCode(requestCode: Int): Single<Int>
    }
}