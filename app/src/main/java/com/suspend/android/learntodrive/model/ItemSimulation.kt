package com.suspend.android.learntodrive.model

import androidx.annotation.ColorRes
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.DiffUtil
import com.suspend.android.learntodrive.R

enum class ItemSimulation(
    @StringRes val title: Int,
    @RawRes val icon: Int,
    @ColorRes val color: Int
) {

    THEORETICAL(
        R.string.title_learn_simulation,
        R.raw.driving_car,
        R.color.bg_layout_home_theoretical
    ); /*,
    TIPS_ANSWER(
        R.string.title_tip_answer,
        R.raw.car_accident_real_view,
        R.color.bg_layout_home_tips
    );*/


    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ItemSimulation>() {
            override fun areItemsTheSame(
                oldItem: ItemSimulation,
                newItem: ItemSimulation
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ItemSimulation,
                newItem: ItemSimulation
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}