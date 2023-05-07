package com.suspend.android.learntodrive.ui.reviewtest

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

class CustomRecyclerView(context: Context, attrs: AttributeSet) : RecyclerView(context, attrs) {

    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        if (e?.action == MotionEvent.ACTION_DOWN) {
            val rv = findChildViewUnder(e.x, e.y)
            if (rv != null && rv.canScrollVertically(-1)) {
                parent?.requestDisallowInterceptTouchEvent(true)
            }
        }
        return super.onInterceptTouchEvent(e)
    }

    override fun onTouchEvent(e: MotionEvent?): Boolean {
        when (e?.action) {
            MotionEvent.ACTION_DOWN -> {
                val rv = findChildViewUnder(e.x, e.y)
                if (rv != null && rv.canScrollVertically(-1)) {
                    parent?.requestDisallowInterceptTouchEvent(true)
                }
            }
            MotionEvent.ACTION_UP -> parent?.requestDisallowInterceptTouchEvent(false)
        }
        return super.onTouchEvent(e)
    }
}