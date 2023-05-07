package com.suspend.android.learntodrive.ui.list_exam_set

import androidx.navigation.fragment.findNavController
import com.suspend.android.learntodrive.base.BaseFragment
import com.suspend.android.learntodrive.databinding.FragmentListExamSetBinding
import com.suspend.android.learntodrive.model.ExamSet
import com.suspend.android.learntodrive.ui.test.TYPE
import com.suspend.android.learntodrive.utils.extension.navigateSafe
import org.koin.androidx.viewmodel.ext.android.viewModel


class ListExamSetFragment :
    BaseFragment<FragmentListExamSetBinding>(FragmentListExamSetBinding::inflate) {
    override val viewModel by viewModel<ListExamTestViewModel>()

    private val listAdapterExamSet by lazy { ListAdapterExamSet(::onClick) }
    override fun initData() {
        viewModel.getExamSetList()
    }

    override fun initView() {
        binding.recyclerViewExamSet.adapter = listAdapterExamSet
        listAdapterExamSet.enableShowNoDataUI(binding.layoutNoData)
        viewModel.examSet.observe(this) {
            listAdapterExamSet.submitList(it)
        }
        binding.imageViewExit.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun initEvent() {

    }

    private fun onClick(item: ExamSet) {
        findNavController().navigateSafe(
            ListExamSetFragmentDirections.actionNaviagtionListExamSetFragmentToNavigationPrepareTest(
                bundleType = TYPE.TEST, bundleIndexExamSet = item.id
            )
        )
    }

}