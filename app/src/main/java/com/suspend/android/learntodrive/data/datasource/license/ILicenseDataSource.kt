package com.suspend.android.learntodrive.data.datasource.license

import com.suspend.android.learntodrive.model.LicenseEntity
import com.suspend.android.learntodrive.model.QuestionEntity
import io.reactivex.rxjava3.core.Single

interface ILicenseDataSource {
    interface Local{
        fun getAll(): Single<List<LicenseEntity>>
        fun getLicenseByName(name:String): Single<LicenseEntity>
    }
}