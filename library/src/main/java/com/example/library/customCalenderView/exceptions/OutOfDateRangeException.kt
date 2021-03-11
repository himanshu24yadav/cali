package com.example.library.customCalenderView.exceptions

/**
 * Created by Himanshu.
 */

data class OutOfDateRangeException(override val message: String) : Exception(message)