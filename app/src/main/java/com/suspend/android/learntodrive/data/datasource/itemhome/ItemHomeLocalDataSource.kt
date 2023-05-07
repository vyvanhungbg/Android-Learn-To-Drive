package com.suspend.android.learntodrive.data.datasource.itemhome

import android.content.Context
import com.suspend.android.learntodrive.model.ItemHome

class ItemHomeLocalDataSource : IItemHomeDataSource.Local {

    override fun getAllItemHome(context: Context): List<ItemHome> {
        return mutableListOf(*ItemHome.values())
    }

}