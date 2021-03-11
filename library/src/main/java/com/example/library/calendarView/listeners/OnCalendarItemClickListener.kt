package com.example.library.calendarView.listeners

import com.example.library.calendarView.CalenderView
import com.example.library.calendarView.EventDay

/**
 * This interface is used to handle clicks on calendar cells
 *
 * Created by Himanshu.
 */

interface OnCalendarItemClickListener {
    fun onCalendarItemClick(eventDay: EventDay, calendarType:Int? = CalenderView.ONE_DAY_PICKER)
}
