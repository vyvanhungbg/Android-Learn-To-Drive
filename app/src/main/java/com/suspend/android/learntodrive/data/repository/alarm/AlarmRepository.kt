package com.suspend.android.learntodrive.data.repository.alarm

import com.suspend.android.learntodrive.data.datasource.alarm.IAlarmDataSource
import com.suspend.android.learntodrive.model.AlarmEntity
import com.suspend.android.learntodrive.model.DayOfWeek
import io.reactivex.rxjava3.core.Single

class AlarmRepository(private val local: IAlarmDataSource.Local) : IAlarmRepository {
    override fun getAll(): Single<List<AlarmEntity>> {
        return local.getAll()
    }

    override fun getByDayRedmine(dayOfWeek: DayOfWeek): Single<List<AlarmEntity>> {
        return local.getByDayRedmine(dayOfWeek)
    }

    override fun getByID(id: Int): Single<AlarmEntity> {
        return local.getByID(id)
    }

    override fun insert(entity: AlarmEntity): Single<Long> {
        return local.insert(entity)
    }

    override fun update(entity: AlarmEntity): Single<Int> {
        return local.update(entity)
    }

    override fun deleteByID(id: Int): Single<Int> {
        return local.deleteByID(id)
    }

    /*override fun deleteByRequestCode(requestCode: Int): Single<Int> {
        return local.deleteByRequestCode(requestCode)
    }*/
}