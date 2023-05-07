package com.suspend.android.learntodrive.data.repository.tips

import com.suspend.android.learntodrive.model.TipEntity
import io.reactivex.rxjava3.core.Single

interface ITipRepository {

    fun getAll(): Single<List<TipEntity>>
}