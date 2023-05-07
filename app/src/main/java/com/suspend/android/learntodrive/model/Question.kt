package com.suspend.android.learntodrive.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize

@Parcelize
data class Question(
    val id: Int,
    val content: String?,
    val answer: List<Answer>,
    val image: String?,
    val answerCorrectPosition: Int?,
    var answerSelected: Int = DEFAULT_ANSWER_SELECTED, // lựa chọn của người dùng
    val questionDie: Boolean = false,
    val description: String?,
    var currentSelected: Boolean = false  // câu hiện tại hiển thị trên slidingpane
) : Parcelable {
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Question>() {
            override fun areItemsTheSame(oldItem: Question, newItem: Question): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Question, newItem: Question): Boolean {
                return oldItem == newItem
            }

        }
        const val DEFAULT_ANSWER_SELECTED = 0
    }
}

fun Question.toText(): String {
    var string = "";
    if (!image.isNullOrEmpty()) {
        string = string.plus("Cho hình, ")
    }
    string = string.plus(content)
    answer.forEach { string = string.plus(", Đáp án ${it.position}, " + it.content) }

    return string
}