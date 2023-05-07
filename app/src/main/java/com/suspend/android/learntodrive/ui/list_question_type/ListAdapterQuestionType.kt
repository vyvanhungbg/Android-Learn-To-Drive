package com.suspend.android.learntodrive.ui.list_question_type

import android.view.LayoutInflater
import android.view.ViewGroup
import com.suspend.android.learntodrive.base.BaseAdapter
import com.suspend.android.learntodrive.base.BaseViewHolder
import com.suspend.android.learntodrive.databinding.ItemQuestionTypeBinding
import com.suspend.android.learntodrive.model.QuestionTypeEntity
import com.suspend.android.learntodrive.utils.constant.Constant
import com.suspend.android.learntodrive.utils.extension.loadImage

class ListAdapterQuestionType(private val onClick: (QuestionTypeEntity) -> Unit) :
    BaseAdapter<QuestionTypeEntity, BaseViewHolder<QuestionTypeEntity>>(QuestionTypeEntity.diffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<QuestionTypeEntity> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemQuestionTypeBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(private val binding: ItemQuestionTypeBinding) :
        BaseViewHolder<QuestionTypeEntity>(binding) {
        override fun binView(item: QuestionTypeEntity) {
            super.binView(item)

            binding.apply {
                binding.imageViewQuestionType.loadImage(Constant.PATH.IMAGES + item.image + Constant.PATH.EXTENSION_IMAGE)
                binding.textViewTitle.text = item.name
                binding.textViewContent.text = item.description
                root.setOnClickListener{
                    onClick(item)
                }
            }
        }
    }
}