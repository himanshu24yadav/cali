package com.himanshu24yadav.cali.calendarView.exceptions

/**
 * Created by Himanshu.
 */

data class OutOfDateRangeException(override val message: String) : Exception(message)