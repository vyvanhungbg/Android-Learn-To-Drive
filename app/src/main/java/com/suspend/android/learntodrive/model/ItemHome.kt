package com.suspend.android.learntodrive.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.DiffUtil
import com.suspend.android.learntodrive.R

enum class ItemHome(
    @StringRes val title: Int,
    @RawRes val icon: Int,
    @ColorRes val color: Int
) {
    RANDOM(
        R.string.title_random_question,
        R.raw.random,
        R.color.bg_layout_home_random
    ),
    EXAM_SET(
        R.string.title_exam_set,
        R.raw.exam_set_test,
        R.color.bg_layout_home_random
    ),
    THEORETICAL(
        R.string.title_theoretical,
        R.raw.theoretical,
        R.color.bg_layout_home_theoretical
    ),
    TRAFFIC_SIGNS(
        R.string.title_traffic_signs,
        R.raw.traffic_lights,
        R.color.bg_layout_home_traffic_signs
    ),
    WRONG_ANSWER(
        R.string.title_wrong_answer,
        R.raw.test_history,
        R.color.bg_layout_home_wrong_answer
    ),
    TIPS_ANSWER(
        R.string.title_tip_answer,
        R.raw.tip,
        R.color.bg_layout_home_tips
    ),
    DIE_QUESTION(
        R.string.title_required_answer,
        R.raw.die_question,
        R.color.bg_layout_home_required_answer
    );

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ItemHome>() {
            override fun areItemsTheSame(oldItem: ItemHome, newItem: ItemHome): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ItemHome, newItem: ItemHome): Boolean {
                return oldItem == newItem
            }

        }
    }
}


