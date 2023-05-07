package com.suspend.android.learntodrive.data.repository.tips

import com.suspend.android.learntodrive.data.datasource.tips.ITipDataSource
import com.suspend.android.learntodrive.model.TipEntity
import io.reactivex.rxjava3.core.Single

class TipRepository(private val local: ITipDataSource.Local) : ITipRepository {

    override fun getAll(): Single<List<TipEntity>> {
        return local.getAll()
    }
}