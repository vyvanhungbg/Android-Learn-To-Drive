package com.suspend.android.learntodrive.ui.trafficsign

import android.view.LayoutInflater
import android.view.ViewGroup
import com.suspend.android.learntodrive.base.BaseAdapter
import com.suspend.android.learntodrive.base.BaseViewHolder
import com.suspend.android.learntodrive.databinding.ItemTrafficSignBinding
import com.suspend.android.learntodrive.model.TrafficSignEntity
import com.suspend.android.learntodrive.utils.constant.Constant
import com.suspend.android.learntodrive.utils.extension.loadImage

class ListAdapterTrafficSigns(private val onClick: (TrafficSignEntity) -> Unit) :
    BaseAdapter<TrafficSignEntity, BaseViewHolder<TrafficSignEntity>>(TrafficSignEntity.diffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<TrafficSignEntity> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTrafficSignBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(private val binding: ItemTrafficSignBinding) :
        BaseViewHolder<TrafficSignEntity>(binding) {
        override fun binView(item: TrafficSignEntity) {
            super.binView(item)
            binding.apply {
                imageViewTrafficSign.loadImage(Constant.PATH.TRAFFIC_SIGN + item.path)
                textViewNameTrafficSign.text = "Biển số ${item.image}: ${item.name}"
                textViewDescriptionTrafficSign.text = item.description
                imageViewTrafficSign.setOnClickListener {
                    onClick(item)
                }
            }
        }
    }
}