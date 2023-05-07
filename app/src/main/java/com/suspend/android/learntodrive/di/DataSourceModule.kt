package com.suspend.android.learntodrive.di

import com.suspend.android.learntodrive.data.datasource.alarm.AlarmLocalDataSource
import com.suspend.android.learntodrive.data.datasource.alarm.IAlarmDataSource
import com.suspend.android.learntodrive.data.datasource.exam_set.ExamSetLocalDataSource
import com.suspend.android.learntodrive.data.datasource.exam_set.IExamSetDataSource
import com.suspend.android.learntodrive.data.datasource.itemhome.IItemHomeDataSource
import com.suspend.android.learntodrive.data.datasource.itemhome.ItemHomeLocalDataSource
import com.suspend.android.learntodrive.data.datasource.license.ILicenseDataSource
import com.suspend.android.learntodrive.data.datasource.license.LicenseLocalDataSource
import com.suspend.android.learntodrive.data.datasource.question.IQuestionDataSource
import com.suspend.android.learntodrive.data.datasource.question.QuestionLocalDataSource
import com.suspend.android.learntodrive.data.datasource.question_simulation.IQuestionSimulationDataSource
import com.suspend.android.learntodrive.data.datasource.question_simulation.QuestionSimulationLocalDataSource
import com.suspend.android.learntodrive.data.datasource.question_simulation_type.IQuestionSimulationTypeDataSource
import com.suspend.android.learntodrive.data.datasource.question_simulation_type.QuestionSimulationTypeLocalDataSource
import com.suspend.android.learntodrive.data.datasource.question_type.IQuestionTypeDataSource
import com.suspend.android.learntodrive.data.datasource.question_type.QuestionTypeLocalDataSource
import com.suspend.android.learntodrive.data.datasource.test.ITestDataSource
import com.suspend.android.learntodrive.data.datasource.test.TestDataSource
import com.suspend.android.learntodrive.data.datasource.tips.ITipDataSource
import com.suspend.android.learntodrive.data.datasource.tips.TipLocalDataSource
import com.suspend.android.learntodrive.data.datasource.trafficsigns.ITrafficSignDataSource
import com.suspend.android.learntodrive.data.datasource.trafficsigns.TrafficSignDataSource
import org.koin.dsl.module

val dataSourceModule = module {
    single<IQuestionDataSource.Local> { QuestionLocalDataSource(get()) }
    single<IItemHomeDataSource.Local> { ItemHomeLocalDataSource() }
    single<ITestDataSource.Local> { TestDataSource(get()) }
    single<ITrafficSignDataSource.Local> { TrafficSignDataSource(get()) }
    single<IQuestionTypeDataSource.Local> { QuestionTypeLocalDataSource(get()) }
    single<ILicenseDataSource.Local> { LicenseLocalDataSource(get()) }
    single<IExamSetDataSource.Local> { ExamSetLocalDataSource(get()) }
    single<IQuestionSimulationDataSource.Local> { QuestionSimulationLocalDataSource(get()) }
    single<IQuestionSimulationTypeDataSource.Local> { QuestionSimulationTypeLocalDataSource(get()) }
    single<IAlarmDataSource.Local> { AlarmLocalDataSource(get()) }
    single<ITipDataSource.Local> { TipLocalDataSource(get()) }
}