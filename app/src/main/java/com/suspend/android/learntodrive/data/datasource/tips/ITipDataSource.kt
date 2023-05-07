package com.suspend.android.learntodrive.data.datasource.tips

import com.suspend.android.learntodrive.model.QuestionTypeEntity
import com.suspend.android.learntodrive.model.TipEntity
import io.reactivex.rxjava3.core.Single

interface ITipDataSource {

    interface Local {
        fun getAll(): Single<List<TipEntity>>
    }
}