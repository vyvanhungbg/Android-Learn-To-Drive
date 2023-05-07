package com.suspend.android.learntodrive.videoplayer

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import com.suspend.android.learntodrive.R
import com.suspend.android.learntodrive.base.BaseAdapter
import com.suspend.android.learntodrive.base.BaseViewHolder
import com.suspend.android.learntodrive.databinding.ItemQuestionSimulationInTestBinding
import com.suspend.android.learntodrive.model.VideoSimulation
import com.suspend.android.learntodrive.utils.extension.color


class ListAdapterVideoSimulationItem(
    private val onClick: (VideoSimulation, Int) -> Unit
) :
    BaseAdapter<VideoSimulation, BaseViewHolder<VideoSimulation>>(VideoSimulation.diffUtil){

    private var currentItemSelected = 0
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<VideoSimulation> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemQuestionSimulationInTestBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }


    fun updateCurrentItemSelected(position: Int){
        this.currentItemSelected = position
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemQuestionSimulationInTestBinding) :
        BaseViewHolder<VideoSimulation>(binding) {

        override fun binView(item: VideoSimulation) {
            super.binView(item)
            val context = binding.root.context
            binding.apply {

                textViewSttQuestion.text = bindingAdapterPosition.inc().toString()
                textViewTitle.text = context.getString(
                    R.string.title_question_simulation_in_test,
                    item.id.toString()
                )
                textViewContent.text = item.description
                if (item.isSelected) {
                    if(item.currentScore > 0){
                        val colorSelected = context.color(R.color.color_green_700)
                        cardViewScore.setCardBackgroundColor(ColorStateList.valueOf(colorSelected))
                    }else{
                        val colorSelected = context.color(R.color.color_main_700)
                        cardViewScore.setCardBackgroundColor(ColorStateList.valueOf(colorSelected))
                    }
                    textViewScore.text = context.getString(
                        R.string.title_score_simulation_in_test,
                        item.currentScore.toString()
                    )
                } else {
                    val colorSelected = context.color(R.color.color_gray_200)
                    textViewScore.text =
                        context.getString(R.string.title_no_score_simulation_in_test)
                   // cardViewScore.setCardBackgroundColor(ColorStateList.valueOf(colorSelected))
                }
                val colorSelected = context.color(R.color.color_red_700)
                val colorUnSelected = context.color(R.color.color_gray_200)
                if(currentItemSelected == bindingAdapterPosition){
                    cardViewItem.setCardBackgroundColor(
                        ColorStateList.valueOf(colorSelected)
                    )
                }else{
                    cardViewItem.setCardBackgroundColor(
                        ColorStateList.valueOf(colorUnSelected)
                    )
                }
                cardViewItem.setOnClickListener {
                    onClick(item, bindingAdapterPosition)
                }
            }
        }
    }
}