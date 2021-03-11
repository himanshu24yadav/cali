package com.example.library.calendarView.utils

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import com.example.library.R
import com.example.library.calendarView.CalenderView.Companion.CLASSIC
import com.example.library.calendarView.CalenderView.Companion.MONTH_PICKER
import com.example.library.calendarView.CalenderView.Companion.ONE_DAY_PICKER
import com.example.library.calendarView.CalenderView.Companion.WEEKLY_RANGE_PICKER
import kotlinx.android.synthetic.main.calendar_view.view.*


/**
 * Created by Himanshu.
 */

internal fun View.setAbbreviationsLabels(color: Int, firstDayOfWeek: Int) {
    val labels = getAbbreviationsTextViews()

    val abbreviations = context.resources.getStringArray(R.array.material_calendar_day_abbreviations_array)

    labels.forEachIndexed { index, label ->
        label.text = abbreviations[(index + firstDayOfWeek - 1) % 7]

        if (color != 0) {
            label.setTextColor(color)
        }
    }
}

private fun View.getAbbreviationsTextViews() = listOf(
        mondayLabel,
        tuesdayLabel,
        wednesdayLabel,
        thursdayLabel,
        fridayLabel,
        saturdayLabel,
        sundayLabel)

internal fun View.setTypeface(typeface: Typeface?) {
    if (typeface == null) return
    getAbbreviationsTextViews().forEach { label ->
        label.typeface = typeface
    }
}

internal fun View.setHeaderColor(color: Int) {
    if (color == 0) return
    this.calendarHeader.setBackgroundColor(color)
}

internal fun View.setHeaderLabelColor(color: Int) {
    if (color == 0) return
    this.currentDateLabel.setTextColor(color)
}

internal fun View.setHeaderTypeface(typeface: Typeface?) {
    if (typeface == null) return
    this.currentDateLabel.typeface = typeface
}

internal fun View.setAbbreviationsBarColor(color: Int) {
    if (color == 0) return
    this.abbreviationsBar.setBackgroundColor(color)
}

internal fun View.setPagesColor(color: Int) {
    if (color == 0) return
    this.calendarViewPager.setBackgroundColor(color)
}

internal fun View.setPreviousButtonImage(drawable: Drawable?) {
    if (drawable == null) return
    this.previousButton.setImageDrawable(drawable)
}

internal fun View.setForwardButtonImage(drawable: Drawable?) {
    if (drawable == null) return
    this.forwardButton.setImageDrawable(drawable)
}

internal fun View.setHeaderVisibility(visibility: Int) {
    this.calendarHeader.visibility = visibility
}

internal fun View.setNavigationVisibility(visibility: Int) {
    this.previousButton.visibility = visibility
    this.forwardButton.visibility = visibility
}

internal fun View.setAbbreviationsBarVisibility(visibility: Int) {
    this.abbreviationsBar.visibility = visibility
}

internal fun View.setSelectionTypeVisibility(toShow: Boolean) {
    this.llCalenderSelectionTypes.setVisibilityOnCondition(toShow)
}

internal fun View.setDaySelectionType(selectionType:Int) : Int {
    return when(selectionType) {
        ONE_DAY_PICKER -> {
            this.tvDailyTextCV.setTextColor(ContextCompat.getColor(this.context, R.color.colorPrimary))
            this.tvWeeklyTextCV.setTextColor(ContextCompat.getColor(this.context, R.color.light_grey))
            this.tvMonthlyTextCV.setTextColor(ContextCompat.getColor(this.context, R.color.light_grey))

            this.tvDailyTextCV.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, R.drawable.ic_rect_red)
            this.tvWeeklyTextCV.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0)
            this.tvMonthlyTextCV.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0)

            ONE_DAY_PICKER
        }
        WEEKLY_RANGE_PICKER -> {
            this.tvWeeklyTextCV.setTextColor(ContextCompat.getColor(this.context, R.color.colorPrimary))
            this.tvDailyTextCV.setTextColor(ContextCompat.getColor(this.context, R.color.light_grey))
            this.tvMonthlyTextCV.setTextColor(ContextCompat.getColor(this.context, R.color.light_grey))

            this.tvWeeklyTextCV.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, R.drawable.ic_rect_red)
            this.tvDailyTextCV.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0)
            this.tvMonthlyTextCV.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0)

            WEEKLY_RANGE_PICKER
        }
        MONTH_PICKER -> {
            this.tvMonthlyTextCV.setTextColor(ContextCompat.getColor(this.context, R.color.colorPrimary))
            this.tvWeeklyTextCV.setTextColor(ContextCompat.getColor(this.context, R.color.light_grey))
            this.tvDailyTextCV.setTextColor(ContextCompat.getColor(this.context, R.color.light_grey))

            this.tvMonthlyTextCV.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, R.drawable.ic_rect_red)
            this.tvWeeklyTextCV.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0)
            this.tvDailyTextCV.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0)

            MONTH_PICKER
        }
        else -> { CLASSIC }
    }
}
