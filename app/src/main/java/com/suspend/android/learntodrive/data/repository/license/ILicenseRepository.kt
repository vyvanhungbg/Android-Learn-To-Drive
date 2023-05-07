package com.suspend.android.learntodrive.data.repository.license

import com.suspend.android.learntodrive.model.LicenseEntity
import io.reactivex.rxjava3.core.Single

interface ILicenseRepository {
    fun getAll(): Single<List<LicenseEntity>>
    fun getLicenseByName(name:String): Single<LicenseEntity>
}