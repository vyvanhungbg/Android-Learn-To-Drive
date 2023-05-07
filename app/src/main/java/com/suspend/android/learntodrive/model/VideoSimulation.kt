package com.suspend.android.learntodrive.model

import androidx.recyclerview.widget.DiffUtil


data class VideoSimulation(
    val id: Int,
    val instruction: String,
    val simulationType: Int,
    val description: String,
    val score: Int,
    val startTime: Int,
    val streamURL: String,
    val currentScore: Int = 0, // điểm thi của người dùng
    val currentTimeSelected: Long = 0, // điểm thi của người dùng
    val totalTimeVideo: Long = 0, // điểm thi của người dùng
    val isSelected: Boolean = false
) {
    fun getListTimeHasCore() =
        listOf(
            startTime,
            startTime + 1,
            startTime + 2,
            startTime + 3,
            startTime + 4
        )

    // nếu chọn vào startTime thì được 5 điểm mỗi mốc sau sẽ bị trừ 1 điểm
    fun calCore(timeSelected: Long): Int {
        val timeSelectedRounded = (timeSelected / 1000).toInt()
        val timeHasCore = getListTimeHasCore().find { it == timeSelectedRounded }
        return if (timeHasCore == null) {
            0
        } else {
            val myScore = score - (timeHasCore - startTime)
            if (myScore > 0) myScore else 0
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<VideoSimulation>() {
            override fun areItemsTheSame(
                oldItem: VideoSimulation,
                newItem: VideoSimulation
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: VideoSimulation,
                newItem: VideoSimulation
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}