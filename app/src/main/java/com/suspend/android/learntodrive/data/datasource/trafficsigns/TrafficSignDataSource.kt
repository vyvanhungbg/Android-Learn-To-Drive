package com.suspend.android.learntodrive.data.datasource.trafficsigns

import com.suspend.android.learntodrive.data.database.system.DatabaseLocal
import com.suspend.android.learntodrive.model.TrafficSignEntity
import com.suspend.android.learntodrive.model.TrafficSignTypeEntity
import io.reactivex.rxjava3.core.Single

class TrafficSignDataSource(private val database : DatabaseLocal): ITrafficSignDataSource.Local {
    override fun getAllTypeTrafficSigns(): Single<List<TrafficSignTypeEntity>> {
        return  database.trafficSignDao.getAllTypeTrafficSigns()
    }

    override fun getAllTrafficSigns(): Single<List<TrafficSignEntity>> {
        return database.trafficSignDao.getAllTrafficSigns()
    }
}