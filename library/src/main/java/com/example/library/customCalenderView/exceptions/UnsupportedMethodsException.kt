package com.example.library.customCalenderView.exceptions

/**
 * Created by Himanshu.
 */

data class UnsupportedMethodsException(override val message: String) : RuntimeException(message)