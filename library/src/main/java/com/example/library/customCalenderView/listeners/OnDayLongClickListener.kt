package com.example.library.customCalenderView.listeners

import com.example.library.customCalenderView.EventDay

/**
 * This interface is used to handle long clicks on calendar cells
 *
 * Created by Himanshu.
 */

interface OnDayLongClickListener {
    fun onDayLongClick(eventDay: EventDay)
}