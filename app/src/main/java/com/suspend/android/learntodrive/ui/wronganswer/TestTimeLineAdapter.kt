package com.suspend.android.learntodrive.ui.wronganswer

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.vipulasri.timelineview.TimelineView
import com.suspend.android.learntodrive.R
import com.suspend.android.learntodrive.databinding.ItemTestFinishedVerticalBinding
import com.suspend.android.learntodrive.model.TestEntity
import com.suspend.android.learntodrive.utils.extension.color
import com.suspend.android.learntodrive.utils.extension.drawable
import com.suspend.android.learntodrive.utils.extension.toDateTimeForHuman

class TestTimeLineAdapter(private val onClick: (TestEntity) -> Unit) :
    RecyclerView.Adapter<TestTimeLineAdapter.ViewHolder>() {

    private val list = mutableListOf<TestEntity>()


    fun submitList(newList: List<TestEntity>?) {
        newList?.let {
            list.clear()
            list.addAll(it)
            notifyDataSetChanged()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position, itemCount)
    }

    inner class ViewHolder(private val binding: ItemTestFinishedVerticalBinding, viewType: Int) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: TestEntity) {
            val hasDieQuestion = item.questions?.filter { it.questionDie }
                ?.any { it.answerSelected != it.answerCorrectPosition } ?: false
            val context = binding.root.context

            when (item.passTest) {
                true -> {
                    binding.timeline.marker = context.drawable(R.drawable.ic_done_circle_24)
                    binding.textViewTimelineStatus.text = item.reason
                    binding.textViewTimelineStatus.setTextColor(
                        ColorStateList.valueOf(
                            context.color(
                                R.color.color_green_700
                            )
                        )
                    )
                }
                false -> {
                    if (hasDieQuestion) {
                        binding.timeline.marker = context.drawable(R.drawable.ic_wrong_circle_24)
                    } else {
                        binding.timeline.marker = context.drawable(R.drawable.ic_failed_circle_24)
                    }
                    binding.textViewTimelineStatus.text = "Trượt (" + item.reason + ")"
                    binding.textViewTimelineStatus.setTextColor(
                        ColorStateList.valueOf(
                            context.color(
                                R.color.color_red
                            )
                        )
                    )
                }
            }

            binding.textViewTimelineDate.text = item.createdAt?.toDateTimeForHuman()
            binding.textViewTimelineTitle.text = item.name

            binding.root.setOnClickListener {
                onClick(item)
            }
        }

        init {
            binding.apply {
                timeline.initLine(viewType)
                timeline.setMarkerColor(R.color.bg_layout_home_random)
                timeline.isMarkerInCenter = true

                /*timeline.setStartLineColor(R.color.purple_500, viewType)
                timeline.setEndLineColor(R.color.purple_200, viewType)*/
                timeline.lineStyle = TimelineView.LineStyle.NORMAL
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTestFinishedVerticalBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding, viewType)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.bindView(item)
    }

    override fun getItemCount() = list.size
}