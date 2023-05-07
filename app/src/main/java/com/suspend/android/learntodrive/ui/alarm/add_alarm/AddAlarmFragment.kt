package com.suspend.android.learntodrive.ui.alarm.add_alarm

import androidx.navigation.fragment.findNavController
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.suspend.android.learntodrive.R
import com.suspend.android.learntodrive.base.BaseFragment
import com.suspend.android.learntodrive.databinding.FragmentAddAlarmBinding
import com.suspend.android.learntodrive.model.AlarmEntity
import com.suspend.android.learntodrive.model.DayOfWeek
import com.suspend.android.learntodrive.utils.extension.showToast
import com.suspend.android.learntodrive.utils.util.CustomPeriodicWorkManager
import com.suspend.android.learntodrive.utils.util.DateTimeUtils
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar
import java.util.concurrent.TimeUnit


class AddAlarmFragment : BaseFragment<FragmentAddAlarmBinding>(FragmentAddAlarmBinding::inflate) {

    override val viewModel by viewModel<AddAlarmViewModel>()

    private var hourSelected = 0
    private var minuteSelected = 0


    private val listDayBinding by lazy {
        listOf(
            binding.checkboxSunday,
            binding.checkboxMonday,
            binding.checkboxTuesday,
            binding.checkboxWednesday,
            binding.checkboxThursday,
            binding.checkboxFriday,
            binding.checkboxSaturday
        )
    }

    override fun initData() {

    }

    override fun initView() {

        val currentDateTime = Calendar.getInstance()
        hourSelected = currentDateTime.get(Calendar.HOUR_OF_DAY)
        minuteSelected = currentDateTime.get(Calendar.MINUTE)
        binding.textViewTimePicker.text =
            DateTimeUtils.formatTime(hourSelected, minuteSelected)
        if (binding.textViewTimePicker.text.toString().isNullOrBlank()) {
            binding.textViewTimePicker.text = DateTimeUtils.formatTime(hourSelected, minuteSelected)
        }
        binding.textViewTimePicker.setOnClickListener {
            val materialTimePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setTitleText(getString(R.string.mess_choose_time))
                .setHour(hourSelected)
                .setMinute(minuteSelected)
                .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
                .build()

            materialTimePicker.addOnPositiveButtonClickListener {
                hourSelected = materialTimePicker.hour
                minuteSelected = materialTimePicker.minute
                binding.textViewTimePicker.text =
                    DateTimeUtils.formatTime(hourSelected, minuteSelected)
            }

            materialTimePicker.show(requireActivity().supportFragmentManager, "TIME_PICKER")

        }

        viewModel.stateAddAlarm.observe(viewLifecycleOwner) {
            if (it.equals(context?.getString(R.string.mess_add_alarm_failed))) {
                context?.showToast(it)
            } else {
                context?.showToast(it)
                findNavController().popBackStack()
            }
        }

        binding.imageViewExit.setOnClickListener {
            findNavController().popBackStack()
        }

        // lắng nghe checkboxAll
        binding.checkboxAllDay.setOnClickListener {
            val checkBoxAllChecked = binding.checkboxAllDay.isChecked
            listDayBinding.forEach {
                it.isChecked = checkBoxAllChecked
            }
        }

        // lắng nghe tất cả check khi true or false

        listDayBinding.forEach {
            it.setOnClickListener {
                binding.checkboxAllDay.isChecked = checkAllDayChecked().not()
            }
        }


    }

    fun checkAllDayChecked(): Boolean {
        return listDayBinding.any { it.isChecked.not() }
    }

    override fun initEvent() {
        binding.buttonAddAlarm.setOnClickListener {

            context?.let {

                val dayOfWeeks = getDayRedmine()
                if (dayOfWeeks.isEmpty()) {
                    it.showToast(getString(R.string.mess_required_selected_day_redmine))
                } else {
                    val item = AlarmEntity(
                        description = null,
                        title = binding.textInputEditText.text.toString(),
                        dayRedmine = dayOfWeeks,
                        hour = hourSelected,
                        minute = minuteSelected,
                        enabled = true
                    )
                    viewModel.addAlarm(it, item)
                }

            }
            createWorkManager()

        }
    }

    private fun getDayRedmine(): MutableList<DayOfWeek> {
        val listDayOfWeek = mutableListOf<DayOfWeek>()

        listDayBinding.filter { it.isChecked }.forEach {
            when (it) {
                binding.checkboxMonday -> listDayOfWeek.add(DayOfWeek.MONDAY)
                binding.checkboxTuesday -> listDayOfWeek.add(DayOfWeek.TUESDAY)
                binding.checkboxWednesday -> listDayOfWeek.add(DayOfWeek.WEDNESDAY)
                binding.checkboxThursday -> listDayOfWeek.add(DayOfWeek.THURSDAY)
                binding.checkboxFriday -> listDayOfWeek.add(DayOfWeek.FRIDAY)
                binding.checkboxSaturday -> listDayOfWeek.add(DayOfWeek.SATURDAY)
                binding.checkboxSunday -> listDayOfWeek.add(DayOfWeek.SUNDAY)
            }
        }

        if (binding.checkboxAllDay.isChecked) {
            listDayOfWeek.clear()
            listDayOfWeek.addAll(DayOfWeek.values())
        }
        return listDayOfWeek
    }

    private fun createWorkManager() {

        val workManager = WorkManager.getInstance(requireContext())

        // hủy mọi công việc đang và đã làm để tạo một lịch set mới
        workManager.cancelAllWork()
        val constraint = Constraints.Builder().build()


        /* val dataInput =
             Data.Builder().putString("time", binding.textViewTimePicker.text.toString()).build()*/

        val periodicRequest = PeriodicWorkRequest
            .Builder(CustomPeriodicWorkManager::class.java, 15, TimeUnit.MINUTES)
            //.setInputData(dataInput)
            .setConstraints(constraint)
            .setBackoffCriteria(BackoffPolicy.LINEAR, 45, TimeUnit.SECONDS)
            .build()

        workManager.enqueue(periodicRequest)
    }

}