package com.suspend.android.learntodrive.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExamSet(
    val id: Int,
    val questions: List<Question>,
    val numberOfTestPassed: Int = 0,
    val numberOfTestFailed: Int = 0,
) : Parcelable {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ExamSet>() {
            override fun areItemsTheSame(oldItem: ExamSet, newItem: ExamSet): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ExamSet, newItem: ExamSet): Boolean {
                return oldItem == newItem
            }

        }
    }
}
