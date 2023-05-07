package com.suspend.android.learntodrive.data.datasource.itemhome

import android.content.Context
import com.suspend.android.learntodrive.model.ItemHome

interface IItemHomeDataSource {

    interface Local {
        fun getAllItemHome(context: Context): List<ItemHome>
    }
}