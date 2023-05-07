package com.suspend.android.learntodrive.ui.simulation


import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import com.suspend.android.learntodrive.base.BaseAdapter
import com.suspend.android.learntodrive.base.BaseViewHolder
import com.suspend.android.learntodrive.databinding.ItemHomeBinding
import com.suspend.android.learntodrive.databinding.ItemHomeFeatureBinding
import com.suspend.android.learntodrive.model.ItemSimulation
import com.suspend.android.learntodrive.utils.extension.color
import com.suspend.android.learntodrive.utils.extension.loadImage

class ListAdapterSimulationItem(private val onClick: (ItemSimulation) -> Unit) :
    BaseAdapter<ItemSimulation, BaseViewHolder<ItemSimulation>>(ItemSimulation.diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ItemSimulation> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHomeBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(private val binding: ItemHomeBinding) :
        BaseViewHolder<ItemSimulation>(binding) {
        override fun binView(item: ItemSimulation) {
            super.binView(item)
            val context = binding.root.context
            binding.apply {
                imageViewItem.setAnimation(item.icon)
                textViewTitle.text = binding.root.context.getString(item.title)
               /* cardViewItem.setCardBackgroundColor(
                    ColorStateList.valueOf(context.color(item.color))
                )*/
                cardViewItem.setOnClickListener {
                    onClick(item)
                }
            }
        }
    }
}