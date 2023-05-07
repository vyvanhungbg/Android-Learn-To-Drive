package com.suspend.android.learntodrive.ui.result

import android.view.LayoutInflater
import android.view.ViewGroup
import com.suspend.android.learntodrive.base.BaseAdapter
import com.suspend.android.learntodrive.base.BaseViewHolder
import com.suspend.android.learntodrive.databinding.ItemOptionResultBinding
import com.suspend.android.learntodrive.model.ResultAction
import com.suspend.android.learntodrive.utils.extension.loadImage

class ListAdapterOptionResult(private val onClick: (ResultAction) -> Unit) :
    BaseAdapter<ResultAction, BaseViewHolder<ResultAction>>(ResultAction.diffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ResultAction> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemOptionResultBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(private val binding: ItemOptionResultBinding) :
        BaseViewHolder<ResultAction>(binding) {
        override fun binView(item: ResultAction) {
            super.binView(item)
            binding.apply {
                circleImageViewItem.setImageResource(item.colorRes)
                textViewTitle.text = item.title
                imageViewItem.loadImage(item.icon)
                root.setOnClickListener {
                    onClick(item)
                }
            }
        }
    }
}
