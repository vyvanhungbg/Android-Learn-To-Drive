package com.suspend.android.learntodrive.ui.result

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import com.suspend.android.learntodrive.base.BaseAdapter
import com.suspend.android.learntodrive.base.BaseViewHolder
import com.suspend.android.learntodrive.databinding.ItemResultBinding
import com.suspend.android.learntodrive.model.ResultInfo
import com.suspend.android.learntodrive.utils.extension.color

class ListAdapterResultInfo() :
    BaseAdapter<ResultInfo, BaseViewHolder<ResultInfo>>(ResultInfo.diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ResultInfo> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemResultBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(private val binding: ItemResultBinding) :
        BaseViewHolder<ResultInfo>(binding) {
        override fun binView(item: ResultInfo) {
            super.binView(item)
            binding.apply {
                textViewContent.text = item.content
                textViewTitle.text = item.title
                val color = binding.root.context.color(item.color)
                textViewContent.setTextColor(color)
                textViewTitle.setTextColor(color)
                radioButton.buttonTintList = ColorStateList.valueOf(color)
            }
        }
    }
}