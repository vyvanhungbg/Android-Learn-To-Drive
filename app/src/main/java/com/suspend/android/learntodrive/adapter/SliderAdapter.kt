package com.suspend.android.learntodrive.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.smarteist.autoimageslider.SliderViewAdapter
import com.suspend.android.learntodrive.databinding.ItemSliderBinding
import com.suspend.android.learntodrive.model.Slider
import com.suspend.android.learntodrive.utils.extension.loadImage


class SliderAdapter : SliderViewAdapter<SliderAdapter.SliderAdapterCustom>() {

    private val list = mutableListOf<Slider>()

    override fun getCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterCustom {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSliderBinding.inflate(inflater, parent, false)
        return SliderAdapterCustom(binding)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterCustom, position: Int) {
        val item = list[position]
        viewHolder.bind(item)
    }

    fun submitList(slider: List<Slider>?) {
        slider?.let {
            list.clear()
            list.addAll(it)
            notifyDataSetChanged()
        }
    }

    inner class SliderAdapterCustom(val binding: ItemSliderBinding) : ViewHolder(binding.root) {
        fun bind(item: Slider) {
            item.image.let {
                binding.imageSlider.loadImage(it)
            }
        }
    }
}
