package com.suspend.android.learntodrive.ui.preparetest

import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.suspend.android.learntodrive.base.BaseFragment
import com.suspend.android.learntodrive.R
import com.suspend.android.learntodrive.databinding.FragmentPrepareTestBinding
import com.suspend.android.learntodrive.ui.test.TYPE
import com.suspend.android.learntodrive.utils.extension.navigateSafe
import org.koin.androidx.viewmodel.ext.android.viewModel


class PrepareTestFragment :
    BaseFragment<FragmentPrepareTestBinding>(FragmentPrepareTestBinding::inflate) {

    override val viewModel by viewModel<PrepareTestViewModel>()

    private val safeArgs: PrepareTestFragmentArgs by navArgs()
    override fun initData() {
        viewModel.getCurrentLicense(safeArgs.bundleType,safeArgs.bundleIndexExamSet,safeArgs.bundleItemQuestionType)
    }

    override fun initView() {
        viewModel.test.observe(viewLifecycleOwner) {

            if(safeArgs.bundleType == TYPE.TEST){
                binding.textViewTitleNameExam.text =
                    context?.getString(R.string.title_name_test, it.name)
                binding.textViewTitleTime.text =
                    context?.getString(R.string.title_time_test, it.time.toString())
                binding.textViewTitleTotalQuestion.text =
                    context?.getString(R.string.title_total_question, it.questions.size.toString())
                binding.textViewTitleMinimumQuestion.text =
                    context?.getString(R.string.title_minimum_question, it.minimumQuestion.toString())
                val amountDieQuestion = it.questions.count { it.questionDie }
                binding.textViewTitleDieQuestion.text =
                    context?.getString(R.string.title_die_question, amountDieQuestion.toString())
                binding.buttonConfirm.isEnabled = true

                binding.textViewHeader.text = context?.getString(R.string.title_random_question)
            }else{
                binding.textViewTitleNameExam.text =
                    context?.getString(R.string.title_name_lesson, it.name)
                binding.textViewTitleTime.text =
                    context?.getString(R.string.title_time_lession, it.time.toString(), (it.time / it.questions.size).toString())
                binding.textViewTitleTotalQuestion.text =
                    context?.getString(R.string.title_total_question, it.questions.size.toString())
                binding.textViewTitleMinimumQuestion.isVisible = false
                val amountDieQuestion = it.questions.count { it.questionDie }
                binding.textViewTitleDieQuestion.text =
                    context?.getString(R.string.title_die_question, amountDieQuestion.toString())
                binding.buttonConfirm.isEnabled = true

                binding.textViewHeader.text = context?.getString( R.string.title_theoretical)
            }



        }

        binding.buttonConfirm.setOnClickListener {

            val action = PrepareTestFragmentDirections.actionNavigationPrepareTestToNavigationTest(
                viewModel.test.value,
                safeArgs.bundleType
            )
            findNavController().navigateSafe(action)
        }

        binding.imageViewExit.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun initEvent() {

    }

}