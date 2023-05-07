package com.suspend.android.learntodrive.ui.alarm

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.suspend.android.learntodrive.R
import com.suspend.android.learntodrive.base.BaseAdapter
import com.suspend.android.learntodrive.base.BaseViewHolder
import com.suspend.android.learntodrive.databinding.ItemAlarmBinding
import com.suspend.android.learntodrive.model.AlarmEntity
import com.suspend.android.learntodrive.model.DayOfWeek
import com.suspend.android.learntodrive.model.convertToString
import com.suspend.android.learntodrive.utils.util.DateTimeUtils


class ListAdapterAlarmItem(
    private val onClick: (AlarmEntity) -> Unit,
    private val onEnableButton: (AlarmEntity) -> Unit,
    private val onLongClick: (AlarmEntity) -> Unit
) :
    BaseAdapter<AlarmEntity, BaseViewHolder<AlarmEntity>>(AlarmEntity.diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<AlarmEntity> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAlarmBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(private val binding: ItemAlarmBinding) :
        BaseViewHolder<AlarmEntity>(binding) {
        override fun binView(item: AlarmEntity) {
            super.binView(item)
            val context = binding.root.context
            binding.apply {
                textViewTime.text = DateTimeUtils.formatTime(item.hour, item.minute)
                textViewContent.text = item.description
                switchWidget.isChecked = item.enabled
                textViewDaySelected.text = convertDayRedmineToString(context, item.dayRedmine)
                root.setOnClickListener {
                    onClick(item)
                }
                root.setOnLongClickListener {
                    onLongClick(item)
                    true
                }
                switchWidget.setOnCheckedChangeListener { _, _enable ->
                    val newItem = item.copy(enabled = _enable)
                    onEnableButton(newItem)
                }
            }
        }
    }

    fun convertDayRedmineToString(context: Context, dayRedmine: List<DayOfWeek>): String {
        return if (dayRedmine.size == 7) {
            context.getString(R.string.mess_all_day)
        } else {
            dayRedmine.fold("") { result, day -> "$result${day.convertToString()}, " }
                .removeSuffix(", ")
        }
    }


}