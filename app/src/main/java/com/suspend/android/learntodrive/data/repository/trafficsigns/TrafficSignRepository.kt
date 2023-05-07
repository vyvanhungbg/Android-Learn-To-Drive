package com.suspend.android.learntodrive.data.repository.trafficsigns

import com.suspend.android.learntodrive.data.datasource.trafficsigns.ITrafficSignDataSource
import com.suspend.android.learntodrive.model.TrafficSignEntity
import com.suspend.android.learntodrive.model.TrafficSignTypeEntity
import io.reactivex.rxjava3.core.Single

class TrafficSignRepository(private val local: ITrafficSignDataSource.Local) :
    ITrafficSignRepository {
    override fun getAllTypeTrafficSigns(): Single<List<TrafficSignTypeEntity>> {
        return local.getAllTypeTrafficSigns()
    }

    override fun getAllTrafficSigns(): Single<List<TrafficSignEntity>> {
        return local.getAllTrafficSigns()
    }
}