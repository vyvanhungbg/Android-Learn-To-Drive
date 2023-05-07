package com.suspend.android.learntodrive.ui.question

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import com.suspend.android.learntodrive.base.BaseAdapter
import com.suspend.android.learntodrive.R
import com.suspend.android.learntodrive.base.BaseViewHolder
import com.suspend.android.learntodrive.databinding.ItemOptionBinding
import com.suspend.android.learntodrive.model.Answer
import com.suspend.android.learntodrive.utils.extension.color

class ListAdapterAnswer(
    private val onClick: (Answer) -> Unit,
    private val showAnswerFailed: Boolean,// for mode test or learn
    private val alwaysShowCorrectAnswer: Boolean = false // for mode learn
) :
    BaseAdapter<Answer, BaseViewHolder<Answer>>(Answer.diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Answer> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemOptionBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(private val binding: ItemOptionBinding) :
        BaseViewHolder<Answer>(binding) {
        override fun binView(item: Answer) {
            super.binView(item)
            binding.apply {
                val hasItemChecked = currentList.any { it.checked }

                checkboxItem.isChecked = item.checked
                textViewContent.text = item.content
                if (item.checked) {
                    itemSelected(binding)
                } else {
                    itemUnSelected(binding)
                }

                if ((showAnswerFailed && hasItemChecked && item.isCorrectQuestion) || (item.isCorrectQuestion && alwaysShowCorrectAnswer)) {
                    itemCorrect(binding)
                    if (alwaysShowCorrectAnswer) {
                        binding.checkboxItem.isChecked = true
                    }
                }

                root.setOnClickListener {
                    if (showAnswerFailed) {
                        // không cho chọn lại câu hỏi
                        if (hasItemChecked.not()) {
                            onClick(item)
                        }
                    } else {
                        onClick(item)
                    }
                }
            }
        }
    }


    private fun itemSelected(binding: ItemOptionBinding) {
        val context = binding.root.context
        val colorWhite = context.color(R.color.white)
        val colorSelected = context.color(R.color.color_main_700)
        binding.apply {
            cardViewItem.setCardBackgroundColor(ColorStateList.valueOf(colorSelected))
            textViewContent.setTextColor(colorWhite)
            viewItem.setBackgroundColor(colorWhite)
            checkboxItem.buttonTintList = ColorStateList.valueOf(colorWhite)
        }
    }

    private fun itemUnSelected(binding: ItemOptionBinding) {
        val context = binding.root.context
        val colorWhite = context.color(R.color.white)
        val colorSelected = context.color(R.color.color_main_700)
        val colorTextNormal = context.color(R.color.black)
        binding.apply {
            cardViewItem.setCardBackgroundColor(ColorStateList.valueOf(colorWhite))
            textViewContent.setTextColor(colorTextNormal)
            viewItem.setBackgroundColor(colorSelected)
            checkboxItem.buttonTintList = ColorStateList.valueOf(colorSelected)
        }
    }

    private fun itemCorrect(binding: ItemOptionBinding) {
        val context = binding.root.context
        val colorWhite = context.color(R.color.white)
        val colorSelected = context.color(R.color.color_green_700)
        binding.apply {
            cardViewItem.setCardBackgroundColor(ColorStateList.valueOf(colorSelected))
            textViewContent.setTextColor(colorWhite)
            viewItem.setBackgroundColor(colorWhite)
            checkboxItem.buttonTintList = ColorStateList.valueOf(colorWhite)
        }
    }


}
