package com.example.library.customCalenderView.listeners

import java.util.*

/**
 * This interface is used to inform DatePicker that user selected any days
 *
 * Created by Himanshu.
 */

interface OnSelectDateListener {

    @JvmSuppressWildcards
    fun onSelect(calendar: List<Calendar>)
}