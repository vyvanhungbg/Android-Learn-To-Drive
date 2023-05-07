package com.suspend.android.learntodrive.di

import com.suspend.android.learntodrive.data.repository.alarm.AlarmRepository
import com.suspend.android.learntodrive.data.repository.alarm.IAlarmRepository
import com.suspend.android.learntodrive.data.repository.exam_set.ExamSetRepository
import com.suspend.android.learntodrive.data.repository.exam_set.IExamSetRepository
import com.suspend.android.learntodrive.data.repository.itemhome.IItemHomeRepository
import com.suspend.android.learntodrive.data.repository.itemhome.ItemHomeRepository
import com.suspend.android.learntodrive.data.repository.license.ILicenseRepository
import com.suspend.android.learntodrive.data.repository.license.LicenseRepository
import com.suspend.android.learntodrive.data.repository.question.IQuestionRepository
import com.suspend.android.learntodrive.data.repository.question.QuestionRepository
import com.suspend.android.learntodrive.data.repository.question_simulation.IQuestionSimulationRepository
import com.suspend.android.learntodrive.data.repository.question_simulation.QuestionSimulationRepository
import com.suspend.android.learntodrive.data.repository.question_simulation_type.IQuestionSimulationTypeRepository
import com.suspend.android.learntodrive.data.repository.question_simulation_type.QuestionSimulationTypeRepository
import com.suspend.android.learntodrive.data.repository.question_type.IQuestionTypeRepository
import com.suspend.android.learntodrive.data.repository.question_type.QuestionTypeRepository
import com.suspend.android.learntodrive.data.repository.test.ITestRepository
import com.suspend.android.learntodrive.data.repository.test.TestRepository
import com.suspend.android.learntodrive.data.repository.tips.ITipRepository
import com.suspend.android.learntodrive.data.repository.tips.TipRepository
import com.suspend.android.learntodrive.data.repository.trafficsigns.ITrafficSignRepository
import com.suspend.android.learntodrive.data.repository.trafficsigns.TrafficSignRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<IQuestionRepository> {
        QuestionRepository(get())
    }
    single<IItemHomeRepository> { ItemHomeRepository(get()) }
    single<ITestRepository> { TestRepository(get()) }
    single<ITrafficSignRepository> { TrafficSignRepository(get()) }
    single<IQuestionTypeRepository> { QuestionTypeRepository(get()) }
    single<ILicenseRepository> { LicenseRepository(get()) }
    single<IExamSetRepository> { ExamSetRepository(get()) }
    single<IQuestionSimulationRepository> { QuestionSimulationRepository(get()) }
    single<IQuestionSimulationTypeRepository> { QuestionSimulationTypeRepository(get()) }
    single<IAlarmRepository> { AlarmRepository(get()) }
    single<ITipRepository> { TipRepository(get()) }

}