package com.suspend.android.learntodrive.di

import android.content.Context
import androidx.room.Room
import com.suspend.android.learntodrive.data.database.system.DatabaseLocal
import com.suspend.android.learntodrive.data.database.user.DatabaseUser
import com.suspend.android.learntodrive.utils.constant.Constant
import com.suspend.android.learntodrive.utils.extension.logError
import com.suspend.android.learntodrive.utils.util.ObjectTypeConverters
import org.koin.dsl.module
import java.util.concurrent.Executors

val databaseModule = module {
    single { provideDatabase(get()) }
    single { provideQuestionDAO(get()) }

    single { provideDatabaseUser(get()) }
    single { provideTestDAO(get()) }
    single { provideTrafficSignDAO(get()) }
    single { provideTipDAO(get()) }
}

 fun provideDatabase(context: Context): DatabaseLocal {
    return Room.databaseBuilder(
        context.applicationContext,
        DatabaseLocal::class.java,
        Constant.DB.NAME
    ).setQueryCallback({a,b -> logError(message = "$a, ${b.toString()}") }, Executors.newSingleThreadExecutor())
        .createFromAsset(Constant.DB.PATH)
        .fallbackToDestructiveMigration()
        .build()
}

fun provideDatabaseUser(context: Context): DatabaseUser {
    return Room.databaseBuilder(
        context.applicationContext,
        DatabaseUser::class.java,
        Constant.DB_USER.NAME
    ).addTypeConverter(ObjectTypeConverters())
        .build()
}

private fun provideQuestionDAO(database: DatabaseLocal) = database.questionDao
private fun provideTrafficSignDAO(database: DatabaseLocal) = database.trafficSignDao
private fun provideTestDAO(database: DatabaseUser) = database.testDao
private fun provideTipDAO(database: DatabaseLocal) = database.tipDAO
