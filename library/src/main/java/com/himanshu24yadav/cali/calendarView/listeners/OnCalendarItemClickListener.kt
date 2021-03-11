package com.himanshu24yadav.cali.calendarView.listeners

import com.himanshu24yadav.cali.calendarView.CalenderView
import com.himanshu24yadav.cali.calendarView.EventDay

/**
 * This interface is used to handle clicks on calendar cells
 *
 * Created by Himanshu.
 */

interface OnCalendarItemClickListener {
    fun onCalendarItemClick(eventDay: EventDay, calendarType:Int? = CalenderView.ONE_DAY_PICKER)
}
