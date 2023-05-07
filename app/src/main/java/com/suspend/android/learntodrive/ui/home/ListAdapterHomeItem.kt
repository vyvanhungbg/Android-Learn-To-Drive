package com.suspend.android.learntodrive.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.suspend.android.learntodrive.base.BaseAdapter
import com.suspend.android.learntodrive.base.BaseViewHolder
import com.suspend.android.learntodrive.databinding.ItemHomeBinding
import com.suspend.android.learntodrive.model.ItemHome

class ListAdapterHomeItem(private val onClick: (ItemHome) -> Unit) :
    BaseAdapter<ItemHome, BaseViewHolder<ItemHome>>(ItemHome.diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ItemHome> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHomeBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(private val binding: ItemHomeBinding) :
        BaseViewHolder<ItemHome>(binding) {
        override fun binView(item: ItemHome) {
            super.binView(item)
            val context = binding.root.context
            binding.apply {
                imageViewItem.setAnimation(item.icon)
                textViewTitle.text = binding.root.context.getString(item.title)
                /*cardViewItem.setCardBackgroundColor(
                    ColorStateList.valueOf(context.color(item.color))
                )*/
                cardViewItem.setOnClickListener {
                    onClick(item)
                }
            }
        }
    }
}