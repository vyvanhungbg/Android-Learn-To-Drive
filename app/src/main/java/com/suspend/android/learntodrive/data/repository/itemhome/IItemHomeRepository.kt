package com.suspend.android.learntodrive.data.repository.itemhome

import android.content.Context
import com.suspend.android.learntodrive.model.ItemHome

interface IItemHomeRepository {
    fun getAllItemHome(context: Context): List<ItemHome>
}
