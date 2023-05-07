package com.suspend.android.learntodrive.ui.simulation.simulation_lean

import androidx.navigation.fragment.findNavController
import com.suspend.android.learntodrive.base.BaseFragment
import com.suspend.android.learntodrive.databinding.FragmentLearnSimulationBinding
import com.suspend.android.learntodrive.model.QuestionSimulationTypeEntity
import com.suspend.android.learntodrive.utils.extension.navigateSafe
import org.koin.androidx.viewmodel.ext.android.viewModel

class LearnSimulationFragment :
    BaseFragment<FragmentLearnSimulationBinding>(FragmentLearnSimulationBinding::inflate) {

    override val viewModel by viewModel<LearnSimulationViewModel>()

    private val listAdapterSimulationItem by lazy { ListAdapterLearnSimulationItem(::onClickItem) }

    override fun initData() {
        viewModel.getQuestionSimulationTypeList()
    }

    override fun initView() {
        binding.recyclerViewLearnSimulation.adapter = listAdapterSimulationItem
        viewModel.simulationQuestionTypeList.observe(this) {
            listAdapterSimulationItem.submitList(it)
        }
    }

    override fun initEvent() {

    }

    private fun onClickItem(item: QuestionSimulationTypeEntity) {
        val destination =
            LearnSimulationFragmentDirections.actionNavigationLearnSimulationFragmentToNavigationVideoPlayer(
                typeQuestion = item.id
            )
        findNavController().navigateSafe(destination)
    }

}