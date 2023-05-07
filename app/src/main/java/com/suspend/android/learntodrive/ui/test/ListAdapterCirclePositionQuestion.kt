package com.suspend.android.learntodrive.ui.test

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import com.suspend.android.learntodrive.base.BaseAdapter
import com.suspend.android.learntodrive.R
import com.suspend.android.learntodrive.base.BaseViewHolder
import com.suspend.android.learntodrive.databinding.ItemCirclePositionQuestionBinding
import com.suspend.android.learntodrive.model.Question
import com.suspend.android.learntodrive.utils.extension.color

class ListAdapterCirclePositionQuestion(
    private val onClick: (Int) -> Unit,
    private val showQuestionFailed: Boolean = true
) :
    BaseAdapter<Question, BaseViewHolder<Question>>(Question.diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Question> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCirclePositionQuestionBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(private val binding: ItemCirclePositionQuestionBinding) :
        BaseViewHolder<Question>(binding) {
        override fun binView(item: Question) {
            super.binView(item)
            binding.apply {
                val position = adapterPosition
                val context = binding.root.context
                textViewPosition.text = position.inc().toString()
                val colorChecked = context.color(R.color.bg_layout_home_required_answer)
                val colorWhite = context.color(R.color.white)

                val colorRed700 = context.color(R.color.color_red_700)

                // hiện lựa chọn
                if (currentList[position].answerSelected != 0) {

                    // hien cau hoi sai
                    if (showQuestionFailed && item.answerCorrectPosition != currentList[position].answerSelected) {
                        circleImageView.setImageResource(R.color.color_red)
                        circleImageView.borderColor = colorWhite
                        textViewPosition.setTextColor(ColorStateList.valueOf(colorWhite))
                    } else if (showQuestionFailed && item.answerCorrectPosition == currentList[position].answerSelected) {
                        circleImageView.setImageResource(R.color.color_green_700)
                        circleImageView.borderColor = colorWhite
                        textViewPosition.setTextColor(ColorStateList.valueOf(colorWhite))
                    } else {
                        circleImageView.setImageResource(R.color.bg_layout_home_required_answer)
                        circleImageView.borderColor = colorWhite
                        textViewPosition.setTextColor(ColorStateList.valueOf(colorWhite))
                    }
                } else {
                    circleImageView.setImageResource(R.color.white)
                    circleImageView.borderColor = colorChecked
                    textViewPosition.setTextColor(ColorStateList.valueOf(colorChecked))
                }

                if (item.currentSelected) {
                    circleImageView.borderColor = colorRed700
                }
                root.setOnClickListener {
                    onClick(position)
                }


            }
        }
    }

}