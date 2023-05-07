package com.suspend.android.learntodrive.ui.wronganswer

import androidx.navigation.fragment.findNavController
import com.suspend.android.learntodrive.base.BaseFragment
import com.suspend.android.learntodrive.databinding.FragmentWrongAnswerBinding
import com.suspend.android.learntodrive.model.TestEntity
import com.suspend.android.learntodrive.utils.extension.hide
import com.suspend.android.learntodrive.utils.extension.navigateSafe
import com.suspend.android.learntodrive.utils.extension.show
import org.koin.androidx.viewmodel.ext.android.viewModel


class WrongAnswerFragment :
    BaseFragment<FragmentWrongAnswerBinding>(FragmentWrongAnswerBinding::inflate) {
    override val viewModel by viewModel<WrongAnswerViewModel>()

    private val adapter by lazy { TestTimeLineAdapter(::onClickItemTestTimeline) }

    override fun initData() {
        viewModel.getAllQuestionsDie()
    }

    override fun initView() {
        binding.recyclerView.adapter = adapter

        viewModel.tests.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            if (it.isEmpty()) {
                binding.recyclerView.hide()
                binding.layoutNoData.show()
            } else {
                binding.recyclerView.show()
                binding.layoutNoData.hide()
            }
        }

        binding.imageViewExit.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun initEvent() {

    }

    private fun onClickItemTestTimeline(test: TestEntity) {
        test.id?.let {
            val action = WrongAnswerFragmentDirections
                .actionNavigationWrongAnswerToNavigationReviewTest(test)
            findNavController().navigateSafe(action)
        }
    }

}
