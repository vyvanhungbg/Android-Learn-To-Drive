package com.suspend.android.learntodrive.ui.list_exam_set

import android.view.LayoutInflater
import android.view.ViewGroup
import com.suspend.android.learntodrive.base.BaseAdapter
import com.suspend.android.learntodrive.base.BaseViewHolder
import com.suspend.android.learntodrive.databinding.ItemExamTestBinding
import com.suspend.android.learntodrive.model.ExamSet

class ListAdapterExamSet(private val onClick: (ExamSet) -> Unit) :
    BaseAdapter<ExamSet, BaseViewHolder<ExamSet>>(ExamSet.diffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ExamSet> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemExamTestBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(private val binding: ItemExamTestBinding) :
        BaseViewHolder<ExamSet>(binding) {
        override fun binView(item: ExamSet) {
            super.binView(item)

            binding.apply {
                binding.textViewNumberOfExam.text = item.id.toString()
                binding.textViewCorrectTest.text = item.numberOfTestPassed.toString()
                binding.textViewFailedTest.text = item.numberOfTestFailed.toString()
                root.setOnClickListener {
                    onClick(item)
                }
            }
        }
    }
}