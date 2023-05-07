package com.suspend.android.learntodrive.ui.simulation.simulation_lean

import android.view.LayoutInflater
import android.view.ViewGroup
import com.suspend.android.learntodrive.base.BaseAdapter
import com.suspend.android.learntodrive.base.BaseViewHolder
import com.suspend.android.learntodrive.databinding.ItemSimulationBinding
import com.suspend.android.learntodrive.model.QuestionSimulationTypeEntity
import com.suspend.android.learntodrive.utils.constant.Constant
import com.suspend.android.learntodrive.utils.extension.loadImage

class ListAdapterLearnSimulationItem(private val onClick: (QuestionSimulationTypeEntity) -> Unit) :
    BaseAdapter<QuestionSimulationTypeEntity, BaseViewHolder<QuestionSimulationTypeEntity>>(
        QuestionSimulationTypeEntity.diffUtil
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<QuestionSimulationTypeEntity> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSimulationBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(private val binding: ItemSimulationBinding) :
        BaseViewHolder<QuestionSimulationTypeEntity>(binding) {
        override fun binView(item: QuestionSimulationTypeEntity) {
            super.binView(item)
            val context = binding.root.context
            binding.apply {
                imageViewImageTop.loadImage(Constant.PATH.IMAGES + item.image + Constant.PATH.EXTENSION_IMAGE)
                textViewTitle.text = item.name
                binding.textViewDescription.text = item.description
                binding.textViewNumberQuestion.text = "${item.numberOfQuestion} câu hỏi"
                root.setOnClickListener {
                    onClick(item)
                }
            }
        }
    }
}