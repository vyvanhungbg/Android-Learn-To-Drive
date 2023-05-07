package com.suspend.android.learntodrive.data.datasource.tips

import com.suspend.android.learntodrive.data.database.system.DatabaseLocal
import com.suspend.android.learntodrive.model.TipEntity
import io.reactivex.rxjava3.core.Single

class TipLocalDataSource(private val databaseLocal: DatabaseLocal) : ITipDataSource.Local {

    override fun getAll(): Single<List<TipEntity>> {
        return databaseLocal.tipDAO.getAll()
    }
}