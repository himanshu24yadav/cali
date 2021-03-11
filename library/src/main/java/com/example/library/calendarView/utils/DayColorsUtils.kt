@file:JvmName("DayColorsUtils")

package com.example.library.calendarView.utils

import android.content.Context
import android.graphics.Typeface
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import com.example.library.R
import com.example.library.calendarView.CalendarDay
import com.example.library.calendarView.CalenderView
import java.util.*


/**
 * This class is used to set a style of calendar cells.
 *
 * Created by Himanshu.
 */

/**
 * This is general method which sets a color of the text, font type and a background of a TextView object.
 * It is used to set day cell (numbers) style.
 *
 * @param this   TextView containing a day number
 * @param textColor  A resource of a color of the day number
 * @param backgroundRes A resource of a background drawable
 */
internal fun TextView.setDayColors(textColor: Int, typeface: Typeface? = null, backgroundRes: Int = R.drawable.background_transparent) {
    typeface?.let { setTypeface(typeface) }
    setTextColor(textColor)
    setBackgroundResource(backgroundRes)
    TextViewCompat.setTextAppearance(this, R.style.TimelineSelected700TextStyle)
}

/**
 * This method sets a color of the text, font type and a background of a TextView object.
 * It is used to set day cell (numbers) style in the case of selected day (when calendar is in
 * the picker mode). It also colors a background of the selection.
 *
 * @param dayLabel           TextView containing a day number
 * @param calendarProperties A resource of a selection background color
 */
internal fun setSelectedDayColors(dayLabel: TextView, calendar: Calendar, calendarProperties: CalendarProperties) {
    val calendarDay = calendarProperties.findDayProperties(calendar)

    val labelColor = calendarDay?.getSelectedLabelColor(dayLabel.context) ?: calendarProperties.selectionLabelColor

    val calendarDayBackgroundRes = calendarDay?.selectedBackgroundResource
    val calendarDayBackgroundDrawable = calendarDay?.selectedBackgroundDrawable

    if (calendarDayBackgroundRes != null && !calendarProperties.selectionDisabled) {
        dayLabel.setDayColors(labelColor,backgroundRes = calendarDayBackgroundRes)
    } else if (calendarDayBackgroundDrawable != null && !calendarProperties.selectionDisabled) {
        dayLabel.setDayColors(labelColor)
        dayLabel.setBackgroundDrawable(calendarDayBackgroundDrawable)
    } else {
        dayLabel.setDayColors(labelColor,backgroundRes = calendarProperties.selectionBackground)
        tintBackground(dayLabel, calendarProperties.selectionColor)
    }

    TextViewCompat.setTextAppearance(dayLabel, R.style.TimelineSelected700TextStyle)
}

/**
 * This method is used to set a color of texts, font types and backgrounds of TextView objects
 * in a current visible month. Visible day labels from previous and forward months are set using
 * setDayColors() method. It also checks if a day number is a day number of today and set it
 * a different color and bold face type.
 *
 * @param calendar           A calendar instance representing day date
 * @param dayLabel           TextView containing a day number
 * @param calendarProperties A resource of a color used to mark today day
 */
internal fun setCurrentMonthDayColors(calendar: Calendar, dayLabel: TextView?, calendarProperties: CalendarProperties) {
    if (dayLabel == null) return

    setNormalDayColors(calendar, dayLabel, calendarProperties)

    val toColorToday = when(calendarProperties.calendarViewType) {
        CalenderView.VIEW_TYPE_MONTH -> {
            calendar.isSameMonth(Calendar.getInstance())
        }
        else -> calendar.isToday
    }

    if (toColorToday) {
        setTodayColors(calendar, dayLabel, calendarProperties)
    }

    if (calendar.isEventDayWithLabelColor(calendarProperties)) {
        setEventDayColors(calendar, dayLabel, calendarProperties)
    }

    if (calendarProperties.highlightedDays.contains(calendar)) {
        setHighlightedDayColors(dayLabel, calendarProperties)
    }
}

private fun setTodayColors(calendar: Calendar, dayLabel: TextView, calendarProperties: CalendarProperties) {
    val calendarDay = calendarProperties.findDayProperties(calendar)

    val calendarDayBackgroundRes = calendarDay?.backgroundResource
    val calendarDayBackgroundDrawable = calendarDay?.backgroundDrawable

    when {
        calendarDayBackgroundRes != null -> {
            dayLabel.setDayColors(
                textColor = calendarProperties.todayLabelColor,
                backgroundRes = calendarProperties.todayBackgroundRes
            )
        }
        calendarDayBackgroundDrawable != null -> {
            dayLabel.setDayColors(
                textColor = calendarProperties.todayLabelColor,
                backgroundRes = calendarProperties.todayBackgroundRes
            )
            dayLabel.setBackgroundDrawable(calendarDayBackgroundDrawable)
        }
        else -> {
            dayLabel.setDayColors(
                textColor = calendarProperties.todayLabelColor,
                backgroundRes = calendarProperties.todayBackgroundRes
            )
        }
    }

    // Sets custom background color for present
    // TODO remove or set as deprecated below functionality in next stable version
    if (calendarProperties.todayColor != 0) {
        dayLabel.setDayColors(
            textColor = calendarProperties.selectionLabelColor,
            backgroundRes = R.drawable.background_color_rect_selector
        )
        tintBackground(dayLabel, calendarProperties.todayColor)
    }

    TextViewCompat.setTextAppearance(dayLabel, R.style.TimelineSelected700TextStyle)
}

private fun setEventDayColors(
    day: Calendar,
    dayLabel: TextView,
    calendarProperties: CalendarProperties
) {
    day.getEventDayWithLabelColor(calendarProperties)?.let { eventDay ->
        dayLabel.setDayColors(textColor = eventDay.labelColor)
    }
}

private fun setHighlightedDayColors(dayLabel: TextView, calendarProperties: CalendarProperties) {
    dayLabel.setDayColors(textColor = calendarProperties.highlightedDaysLabelsColor)
}

private fun setNormalDayColors(calendar: Calendar, dayLabel: TextView, calendarProperties: CalendarProperties) {
    val calendarDay = calendarProperties.findDayProperties(calendar)

    val labelColor = calendarDay?.getLabelColor(dayLabel.context)
            ?: calendarProperties.daysLabelsColor

    val calendarDayBackgroundRes = calendarDay?.backgroundResource
    val calendarDayBackgroundDrawable = calendarDay?.backgroundDrawable

    when {
        calendarDayBackgroundRes != null -> {
            dayLabel.setDayColors(labelColor, backgroundRes = calendarDayBackgroundRes)
        }
        calendarDayBackgroundDrawable != null -> {
            dayLabel.setDayColors(labelColor)
            dayLabel.setBackgroundDrawable(calendarDayBackgroundDrawable)
        }
        else -> {
            dayLabel.setDayColors(labelColor, backgroundRes = R.drawable.background_transparent)
        }
    }
}

 fun tintBackground(dayLabel: TextView, color: Int) {
    dayLabel.background.setColorFilter(color, android.graphics.PorterDuff.Mode.MULTIPLY)
}

private fun CalendarDay.getLabelColor(context: Context): Int? {
    val labelColor = this.labelColor ?: return null
    return context.parseColor(labelColor)
}

internal fun CalendarDay.getSelectedLabelColor(context: Context): Int? {
    val selectedLabelColor = this.selectedLabelColor ?: return null
    return context.parseColor(selectedLabelColor)
}

internal fun Context.parseColor(@ColorRes colorRes: Int) = ContextCompat.getColor(this, colorRes)
