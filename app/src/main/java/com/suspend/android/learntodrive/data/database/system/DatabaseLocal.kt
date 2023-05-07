package com.suspend.android.learntodrive.data.database.system

import androidx.room.Database
import androidx.room.RoomDatabase
import com.suspend.android.learntodrive.data.database.system.dao.ExamSetDAO
import com.suspend.android.learntodrive.data.database.system.dao.LicenseDAO
import com.suspend.android.learntodrive.data.database.system.dao.QuestionDAO
import com.suspend.android.learntodrive.data.database.system.dao.QuestionSimulationDAO
import com.suspend.android.learntodrive.data.database.system.dao.QuestionSimulationTypeDAO
import com.suspend.android.learntodrive.data.database.system.dao.QuestionTypeDAO
import com.suspend.android.learntodrive.data.database.system.dao.TipDAO
import com.suspend.android.learntodrive.data.database.system.dao.TrafficSignDAO
import com.suspend.android.learntodrive.model.ExamSetEntity
import com.suspend.android.learntodrive.model.LicenseEntity
import com.suspend.android.learntodrive.model.QuestionEntity
import com.suspend.android.learntodrive.model.QuestionSimulationEntity
import com.suspend.android.learntodrive.model.QuestionSimulationTypeEntity
import com.suspend.android.learntodrive.model.QuestionTypeEntity
import com.suspend.android.learntodrive.model.TipEntity
import com.suspend.android.learntodrive.model.TrafficSignEntity
import com.suspend.android.learntodrive.model.TrafficSignTypeEntity
import com.suspend.android.learntodrive.utils.constant.Constant

@Database(
    entities = [
        QuestionEntity::class,
        TrafficSignTypeEntity::class,
        TrafficSignEntity::class,
        QuestionTypeEntity::class,
        LicenseEntity::class,
        ExamSetEntity::class,
        QuestionSimulationTypeEntity::class,
        QuestionSimulationEntity::class,
        TipEntity::class],
    version = Constant.DB.VERSION,
    exportSchema = false,
)
abstract class DatabaseLocal : RoomDatabase() {

    abstract val questionDao: QuestionDAO
    abstract val trafficSignDao: TrafficSignDAO
    abstract val questionTypeDao: QuestionTypeDAO
    abstract val licenseDao: LicenseDAO
    abstract val examSetDAO: ExamSetDAO
    abstract val questionSimulationDao: QuestionSimulationDAO
    abstract val questionSimulationTypeDao: QuestionSimulationTypeDAO
    abstract val tipDAO: TipDAO

}
