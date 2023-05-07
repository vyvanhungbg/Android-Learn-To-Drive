package com.suspend.android.learntodrive.di

import com.suspend.android.learntodrive.ui.alarm.AlarmViewModel
import com.suspend.android.learntodrive.ui.alarm.add_alarm.AddAlarmViewModel
import com.suspend.android.learntodrive.ui.diequestion.QuestionDieViewModel
import com.suspend.android.learntodrive.ui.home.HomeViewModel
import com.suspend.android.learntodrive.ui.list_exam_set.ListExamTestViewModel
import com.suspend.android.learntodrive.ui.list_question_type.ListQuestionTypeViewModel
import com.suspend.android.learntodrive.ui.preparetest.PrepareTestViewModel
import com.suspend.android.learntodrive.ui.result.ResultViewModel
import com.suspend.android.learntodrive.ui.reviewtest.ReviewTestViewModel
import com.suspend.android.learntodrive.ui.setting.SettingViewModel
import com.suspend.android.learntodrive.ui.simulation.SimulationViewModel
import com.suspend.android.learntodrive.ui.simulation.simulation_lean.LearnSimulationViewModel
import com.suspend.android.learntodrive.ui.test.TestViewModel
import com.suspend.android.learntodrive.ui.tips.TipsViewModel
import com.suspend.android.learntodrive.ui.trafficsign.TrafficSignViewModel
import com.suspend.android.learntodrive.ui.wronganswer.WrongAnswerViewModel
import com.suspend.android.learntodrive.videoplayer.VideoPlayerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        HomeViewModel(
            get(),
            get(),
            get(),
            get(named(SHARED_PREFERENCES_TYPE.SETTINGS)),
            get()
        )
    }
    viewModel { SettingViewModel() }
    viewModel { SimulationViewModel() }
    viewModel { ReviewTestViewModel(get(), get(named(SHARED_PREFERENCES_TYPE.SETTINGS))) }
    viewModel { TestViewModel(get(), get(named(SHARED_PREFERENCES_TYPE.SETTINGS))) }
    viewModel { ResultViewModel(get(), get(named(SHARED_PREFERENCES_TYPE.SETTINGS))) }
    viewModel {
        PrepareTestViewModel(
            get(),
            get(),
            get(),
            get(named(SHARED_PREFERENCES_TYPE.SETTINGS))
        )
    }
    viewModel { TrafficSignViewModel(get()) }
    viewModel { TipsViewModel() }
    viewModel { VideoPlayerViewModel(get()) }
    viewModel { WrongAnswerViewModel(get(), get(named(SHARED_PREFERENCES_TYPE.SETTINGS))) }
    viewModel { QuestionDieViewModel(get(), get(named(SHARED_PREFERENCES_TYPE.SETTINGS))) }
    viewModel {
        ListQuestionTypeViewModel(
            get(),
            get(),
            get(named(SHARED_PREFERENCES_TYPE.SETTINGS))
        )
    }
    viewModel { ListExamTestViewModel(get(), get(), get(named(SHARED_PREFERENCES_TYPE.SETTINGS))) }
    viewModel { LearnSimulationViewModel(get()) }
    viewModel { AlarmViewModel(get()) }
    viewModel { AddAlarmViewModel(get()) }
}