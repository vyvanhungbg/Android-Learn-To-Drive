package com.suspend.android.learntodrive.data.database.user

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.suspend.android.learntodrive.data.database.user.dao.AlarmDAO
import com.suspend.android.learntodrive.data.database.user.dao.TestDAO
import com.suspend.android.learntodrive.model.AlarmEntity
import com.suspend.android.learntodrive.model.TestEntity
import com.suspend.android.learntodrive.utils.constant.Constant
import com.suspend.android.learntodrive.utils.util.ObjectTypeConverters

@Database(
    entities = [TestEntity::class, AlarmEntity::class],
    version = Constant.DB_USER.VERSION,
    exportSchema = false
)
@TypeConverters(ObjectTypeConverters::class)
abstract class DatabaseUser : RoomDatabase() {
    abstract val testDao: TestDAO
    abstract val alarmDao: AlarmDAO
}
