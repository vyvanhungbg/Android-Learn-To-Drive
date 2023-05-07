package com.suspend.android.learntodrive.ui.list_question_type

import androidx.navigation.fragment.findNavController
import com.suspend.android.learntodrive.base.BaseFragment
import com.suspend.android.learntodrive.databinding.FragmentListQuestionTypeBinding
import com.suspend.android.learntodrive.model.QuestionTypeEntity
import com.suspend.android.learntodrive.ui.test.TYPE
import com.suspend.android.learntodrive.utils.extension.navigateSafe
import org.koin.androidx.viewmodel.ext.android.viewModel


class ListQuestionTypeFragment :
    BaseFragment<FragmentListQuestionTypeBinding>(FragmentListQuestionTypeBinding::inflate) {

    override val viewModel by viewModel<ListQuestionTypeViewModel>()

    private val listAdapterQuestionType by lazy { ListAdapterQuestionType(::onClick) }

    override fun initData() {
        viewModel.getQuestionsByType(context)
    }

    override fun initView() {
        binding.recyclerViewQuestionType.adapter = listAdapterQuestionType
        listAdapterQuestionType.enableShowNoDataUI(binding.layoutNoData)
        viewModel.questionsType.observe(viewLifecycleOwner) {
            listAdapterQuestionType.submitList(it)
        }
    }

    override fun initEvent() {
        binding.imageViewExit.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun onClick(item: QuestionTypeEntity) {
        findNavController().navigateSafe(
            ListQuestionTypeFragmentDirections.actionNavigationListQuestionTypeToNavigationPrepareTest(
                bundleType = TYPE.LEARN, bundleItemQuestionType = item
            )
        )
    }

}