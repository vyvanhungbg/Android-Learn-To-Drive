package com.suspend.android.learntodrive.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize

@Parcelize
data class Answer(
    val position: Int,
    val content: String?,
    val checked: Boolean = false,
    val isCorrectQuestion: Boolean = false
) :
    Parcelable {
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Answer>() {
            override fun areItemsTheSame(oldItem: Answer, newItem: Answer): Boolean {
                return oldItem.position == newItem.position
            }

            override fun areContentsTheSame(oldItem: Answer, newItem: Answer): Boolean {
                return oldItem == newItem
            }

        }
    }
}