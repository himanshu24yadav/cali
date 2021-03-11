package com.example.library.calendarView.exceptions

/**
 * Created by Himanshu.
 */

data class UnsupportedMethodsException(override val message: String) : RuntimeException(message)