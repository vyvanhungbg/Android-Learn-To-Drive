package com.suspend.android.learntodrive.ui.result

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.suspend.android.learntodrive.base.BaseFragment
import com.suspend.android.learntodrive.R
import com.suspend.android.learntodrive.databinding.FragmentResultBinding
import com.suspend.android.learntodrive.model.ResultAction
import com.suspend.android.learntodrive.model.ResultTest
import com.suspend.android.learntodrive.ui.test.TYPE
import com.suspend.android.learntodrive.utils.extension.navigateSafe
import com.suspend.android.learntodrive.utils.extension.showSnackBar
import com.suspend.android.learntodrive.utils.extension.showToast
import com.suspend.android.learntodrive.utils.extension.toMinutesSecondExpand
import gen
import org.koin.androidx.viewmodel.ext.android.viewModel


class ResultFragment : BaseFragment<FragmentResultBinding>(FragmentResultBinding::inflate) {

    override val viewModel by viewModel<ResultViewModel>()

    private var bundleResult: ResultTest? = null

    private var bundleModeTest: TYPE = TYPE.TEST
    private val listAdapterResultInfo by lazy { ListAdapterResultInfo() }
    private val listAdapterOptionResult by lazy { ListAdapterOptionResult(::setOnClickItemResultListener) }

    private val safeArgs: ResultFragmentArgs by navArgs()

    override fun initData() {
        bundleResult = safeArgs.bundleResult
        bundleModeTest = safeArgs.bundleType

        if (bundleResult == null) {
            findNavController().popBackStack()
            context?.showToast(R.string.mess_error)
        } else {
            if (bundleModeTest == TYPE.TEST) {
                viewModel.insertTest(bundleResult!!)
            }
            viewModel.convertToTestEntity(bundleResult!!)
        }

    }

    override fun initView() {
        bundleResult?.let {
            binding.apply {
                textViewCorrectAnswer.text = it.correctAnswer.toString()
                recyclerViewStatistic.adapter = listAdapterResultInfo

                if (it.passTest && safeArgs.bundleType == TYPE.TEST) {
                    textViewTitleCorrectAnswer.text = getString(R.string.mess_passed_exam)
                } else if (it.passTest.not() && safeArgs.bundleType == TYPE.TEST) {
                    textViewTitleCorrectAnswer.text = getString(R.string.mess_failed_exam)
                    binding.recyclerViewAction.showSnackBar(it.reason)
                } else {
                    textViewTitleCorrectAnswer.text = getString(R.string.mess_success_exam)
                    binding.recyclerViewAction.showSnackBar(it.reason)
                }


            }
            viewModel.getListStatistic(it)
            val percentPassTest =
                it.correctAnswer.toFloat() / (it.correctAnswer + it.wrongAnswer + it.ignoreAnswer) * 100
            binding.textViewCorrectAnswer.text = it.timeTest.toMinutesSecondExpand()
            binding.customProgressBar.setProgressWithAnimation(percentPassTest)

        }
        viewModel.listStatistics.observe(viewLifecycleOwner) {
            listAdapterResultInfo.submitList(it)
        }

    }

    override fun initEvent() {
        binding.recyclerViewAction.adapter = listAdapterOptionResult
        val list = mutableListOf(*ResultAction.values())
        listAdapterOptionResult.submitList(list)
    }


    private fun setOnClickItemResultListener(item: ResultAction) {
        when (item) {
            ResultAction.REVIEW_ANSWER -> {
                val testInserted = viewModel.testEntityInserted.value
                if (testInserted != null) {
                    val action =
                        ResultFragmentDirections.actionNavigationResultToNavigationReviewTest(
                            testInserted
                        )
                    findNavController().navigateSafe(action)
                } else {
                    binding.recyclerViewAction.showSnackBar(getString(R.string.text_wait_get_info_test))
                }
            }
            ResultAction.EXPORT_PDF -> {
                context?.let {

                    val testInserted = viewModel.testEntityInserted.value
                    if (testInserted != null && testInserted.questions != null) {


                        gen(requireActivity(), testInserted, binding.root)

                    } else {
                        binding.recyclerViewAction.showSnackBar(getString(R.string.text_wait_get_info_test))
                    }
                }

            }
            else -> {
                findNavController().popBackStack(R.id.navigation_home,false)
            }
        }
    }


}
