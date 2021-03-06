package com.himanshu24yadav.cali.calendarView.utils

import android.view.View
import java.util.*

/**
 * This helper class represent a selected day when calendar is in a picker date mode.
 * It is used to remember a selected calendar cell.
 *
 * @param calendar Calendar instance representing selected cell date
 * @param view     View representing selected calendar cell
 *
 * Created by Himanshu.
 */

data class SelectedMonth @JvmOverloads constructor(val calendar: Calendar, var view: View? = null) {

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is SelectedMonth -> calendar.isTheSameDay(other.calendar)
            is Calendar -> calendar.isTheSameDay(other)
            else -> super.equals(other)
        }
    }

    private fun Calendar.isTheSameDay(calendar: Calendar): Boolean =
        this.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH) &&
                this.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) &&
                this.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
}