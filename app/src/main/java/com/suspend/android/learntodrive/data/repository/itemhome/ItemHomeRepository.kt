package com.suspend.android.learntodrive.data.repository.itemhome

import android.content.Context
import com.suspend.android.learntodrive.data.datasource.itemhome.IItemHomeDataSource
import com.suspend.android.learntodrive.model.ItemHome

class ItemHomeRepository(private val local: IItemHomeDataSource.Local) : IItemHomeRepository {

    override fun getAllItemHome(context: Context): List<ItemHome> {
        return local.getAllItemHome(context)
    }
}