package com.example.library.customCalenderView.listeners

import com.example.library.customCalenderView.CalenderView
import com.example.library.customCalenderView.EventDay

/**
 * This interface is used to handle clicks on calendar cells
 *
 * Created by Himanshu.
 */

interface OnCalendarItemClickListener {
    fun onCalendarItemClick(eventDay: EventDay, calendarType:Int? = CalenderView.ONE_DAY_PICKER)
}
