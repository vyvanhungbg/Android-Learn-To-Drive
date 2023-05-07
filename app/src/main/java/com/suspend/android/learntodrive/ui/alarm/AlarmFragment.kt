package com.suspend.android.learntodrive.ui.alarm

import androidx.navigation.fragment.findNavController
import com.suspend.android.learntodrive.base.BaseFragment
import com.suspend.android.learntodrive.databinding.FragmentAlarmBinding
import com.suspend.android.learntodrive.utils.extension.navigateSafe
import org.koin.androidx.viewmodel.ext.android.viewModel


class AlarmFragment : BaseFragment<FragmentAlarmBinding>(FragmentAlarmBinding::inflate) {

    override val viewModel by viewModel<AlarmViewModel>()

    private val listAdapterAlarmItem by lazy {
        ListAdapterAlarmItem(onClick = {

        }, onLongClick = {
            viewModel.delete(requireContext(), it)
        }, onEnableButton = {
            viewModel.update(it)
        })
    }

    override fun initData() {

    }

    override fun initView() {
        viewModel.getAlarms()
        binding.recyclerView.adapter = listAdapterAlarmItem
        listAdapterAlarmItem.enableShowNoDataUI(binding.layoutNoData)
        viewModel.alarms.observe(viewLifecycleOwner) {
            listAdapterAlarmItem.submitList(it)
        }
    }

    override fun initEvent() {
        binding.imageViewExit.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.imageViewAddAlarm.setOnClickListener {
            findNavController().navigateSafe(AlarmFragmentDirections.actionNavigationAlarmFragmentToNavigationAddAlarmFragment())
        }
    }

}