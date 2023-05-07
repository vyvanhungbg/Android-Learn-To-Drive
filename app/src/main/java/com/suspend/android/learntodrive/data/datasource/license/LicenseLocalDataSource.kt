package com.suspend.android.learntodrive.data.datasource.license

import com.suspend.android.learntodrive.data.database.system.DatabaseLocal
import com.suspend.android.learntodrive.model.LicenseEntity
import io.reactivex.rxjava3.core.Single

class LicenseLocalDataSource(private val database: DatabaseLocal) : ILicenseDataSource.Local {

    override fun getAll(): Single<List<LicenseEntity>> {
        return database.licenseDao.getAll()
    }

    override fun getLicenseByName(name: String): Single<LicenseEntity> {
        return database.licenseDao.getLicenseByName(name)
    }
}