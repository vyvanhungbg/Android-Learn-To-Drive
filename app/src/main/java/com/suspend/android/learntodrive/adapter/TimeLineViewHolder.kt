package com.suspend.android.learntodrive.adapter


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.vipulasri.timelineview.TimelineView
import com.suspend.android.learntodrive.R


class TimeLineViewHolder : RecyclerView.ViewHolder {
    var  _timeLineView : TimelineView? = null;
    val timeLineView get() = _timeLineView!!;

    constructor(itemView: View):super(itemView){

    }
    constructor(itemView: View, viewType:Int): super(itemView){
        _timeLineView = itemView.findViewById<TimelineView>(R.id.timeline)
        timeLineView.initLine(viewType);
    }

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeLineViewHolder? {
        val view = View.inflate(parent.context, R.layout.custom_view_radio_button, null)
        return TimeLineViewHolder(view, viewType)
    }

   /* override get

    fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position, getItemCount())
    }*/
}