package com.himanshu24yadav.cali.calendarView.listeners

import com.himanshu24yadav.cali.calendarView.EventDay

/**
 * This interface is used to handle long clicks on calendar cells
 *
 * Created by Himanshu.
 */

interface OnDayLongClickListener {
    fun onDayLongClick(eventDay: EventDay)
}