package com.suspend.android.learntodrive.data.repository.license

import com.suspend.android.learntodrive.data.datasource.license.ILicenseDataSource
import com.suspend.android.learntodrive.model.LicenseEntity
import io.reactivex.rxjava3.core.Single

class LicenseRepository(private val local: ILicenseDataSource.Local):ILicenseRepository {
    override fun getAll(): Single<List<LicenseEntity>> {
        return local.getAll()
    }

    override fun getLicenseByName(name: String): Single<LicenseEntity> {
        return local.getLicenseByName(name)
    }
}