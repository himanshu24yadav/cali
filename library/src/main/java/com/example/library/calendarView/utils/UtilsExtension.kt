package com.example.library.calendarView.utils

import android.view.View

//Useful when to give single padding and remaining must be same
fun View?.setPaddingToView(padding: Int? = null, topPadding:Int?= null, bottomPadding:Int?=null, rigthPadding:Int?=null, leftPadding:Int?=null) {
    padding?.let {
        this?.setPadding(it, it, it, it)
    } ?: run {
        val leftPaddingToView = leftPadding ?: this?.paddingLeft ?: 0
        val rigthPaddingToView = rigthPadding ?: this?.paddingRight ?: 0
        val topPaddingToView = topPadding ?: this?.paddingTop ?: 0
        val bottomPaddingToView = bottomPadding ?: this?.paddingBottom ?: 0
        this?.setPadding(leftPaddingToView, topPaddingToView, rigthPaddingToView, bottomPaddingToView)
    }
}

fun View?.setVisibilityOnCondition(condition: Boolean) {
    when (condition) {
        true -> {
            this?.visibility = View.VISIBLE
        }
        false -> {
            this?.visibility = View.GONE
        }
    }
}