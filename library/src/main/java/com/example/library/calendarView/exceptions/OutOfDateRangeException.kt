package com.example.library.calendarView.exceptions

/**
 * Created by Himanshu.
 */

data class OutOfDateRangeException(override val message: String) : Exception(message)