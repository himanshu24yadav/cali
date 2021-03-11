package com.example.library.calendarView.listeners

import com.example.library.calendarView.EventDay

/**
 * This interface is used to handle long clicks on calendar cells
 *
 * Created by Himanshu.
 */

interface OnDayLongClickListener {
    fun onDayLongClick(eventDay: EventDay)
}