package com.suspend.android.learntodrive.data.repository.trafficsigns

import com.suspend.android.learntodrive.model.TrafficSignEntity
import com.suspend.android.learntodrive.model.TrafficSignTypeEntity
import io.reactivex.rxjava3.core.Single

interface ITrafficSignRepository {
    fun getAllTypeTrafficSigns(): Single<List<TrafficSignTypeEntity>>
    fun getAllTrafficSigns(): Single<List<TrafficSignEntity>>
}
