package com.suspend.android.learntodrive.model

import androidx.annotation.ColorRes
import androidx.recyclerview.widget.DiffUtil

class ResultInfo(val title: String, val content: String, @ColorRes val color: Int) {
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ResultInfo>() {
            override fun areItemsTheSame(oldItem: ResultInfo, newItem: ResultInfo): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: ResultInfo, newItem: ResultInfo): Boolean {
                return oldItem.content == newItem.content
            }

        }
    }
}
