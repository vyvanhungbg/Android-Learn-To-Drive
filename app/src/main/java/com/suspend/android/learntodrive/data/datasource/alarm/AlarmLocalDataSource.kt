package com.suspend.android.learntodrive.data.datasource.alarm

import com.suspend.android.learntodrive.data.database.user.DatabaseUser
import com.suspend.android.learntodrive.model.AlarmEntity
import com.suspend.android.learntodrive.model.DayOfWeek
import io.reactivex.rxjava3.core.Single

class AlarmLocalDataSource(private val databaseUser: DatabaseUser) : IAlarmDataSource.Local {
    override fun getAll(): Single<List<AlarmEntity>> {
        return databaseUser.alarmDao.getAll()
    }

    override fun getByDayRedmine(dayOfWeek: DayOfWeek): Single<List<AlarmEntity>> {
        return databaseUser.alarmDao.getByDayRedmine(dayOfWeek)
    }

    override fun getByID(id: Int): Single<AlarmEntity> {
        return databaseUser.alarmDao.getByID(id)
    }

    override fun insert(entity: AlarmEntity): Single<Long> {
        return databaseUser.alarmDao.insert(entity)
    }

    override fun update(entity: AlarmEntity): Single<Int> {
        return databaseUser.alarmDao.update(entity)
    }

    override fun deleteByID(id: Int): Single<Int> {
        return databaseUser.alarmDao.deleteByID(id)
    }

    /*override fun deleteByRequestCode(requestCode: Int): Single<Int> {
        return databaseUser.alarmDao.deleteByRequestCode(requestCode)
    }*/
}