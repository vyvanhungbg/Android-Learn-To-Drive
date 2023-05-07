package com.suspend.android.learntodrive.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import com.suspend.android.learntodrive.R

enum class ResultAction(
    val title: String,
    @DrawableRes val icon: Int,
    @ColorRes val colorRes: Int
) {
    DO_AGAIN("Làm lại", R.drawable.ic_replay_24dp, R.color.bg_layout_home_random),
    REVIEW_ANSWER("Xem câu sai", R.drawable.ic_exam_24dp, R.color.bg_layout_home_wrong_answer),
    SHARE("Chia sẻ", R.drawable.ic_share_24dp, R.color.bg_layout_home_tips),
    EXPORT_PDF("Tạo", R.drawable.ic_pdf_24dp, R.color.bg_layout_home_required_answer),
    HOME("Trang chủ", R.drawable.ic_home_white_24dp, R.color.bg_layout_home_traffic_signs),
    REPORT("Báo cáo", R.drawable.ic_report_24dp, R.color.bg_layout_home_theoretical);

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ResultAction>() {
            override fun areItemsTheSame(oldItem: ResultAction, newItem: ResultAction): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: ResultAction, newItem: ResultAction): Boolean {
                return oldItem == newItem
            }

        }
    }
}
