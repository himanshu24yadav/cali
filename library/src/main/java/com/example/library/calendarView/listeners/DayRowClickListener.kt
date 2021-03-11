package com.example.library.calendarView.listeners

import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import com.example.library.R
import com.example.library.calendarView.CalenderView
import com.example.library.calendarView.EventDay
import com.example.library.calendarView.getDatesRange
import com.example.library.calendarView.getEnabledDayFrom
import com.example.library.calendarView.utils.*
import com.example.library.calendarView.utils.setCurrentMonthDayColors
import com.example.library.calendarView.utils.setSelectedDayColors
import com.example.library.calendarView.adapters.CalendarPageAdapter
import java.util.*

/**
 * This class is responsible for handle click events
 *
 *
 * Created by Himanshu.
 */
class DayRowClickListener(private val calendarPageAdapter: CalendarPageAdapter, private val calendarProperties: CalendarProperties, pageMonth: Int) : OnItemClickListener {

    private val pageMonth = if (pageMonth < 0) 11 else pageMonth

    override fun onItemClick(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
        val day = GregorianCalendar().apply {
            time = adapterView.getItemAtPosition(position) as Date
        }

        if (calendarProperties.selectionDisabled) return

        when (calendarProperties.calendarType) {
            CalenderView.ONE_DAY_PICKER -> selectOneDay(view, day)
            CalenderView.MANY_DAYS_PICKER -> selectManyDays(view, day)
            CalenderView.RANGE_PICKER -> selectRange(view, day)
            CalenderView.WEEKLY_RANGE_PICKER -> selectWeeklyRange(view, day)
            CalenderView.CLASSIC -> calendarPageAdapter.selectedDay = SelectedDay(day, view)
        }

        if (calendarProperties.onCalendarItemClickListener != null) {
            onClick(day)
        }
    }

    private fun selectOneDay(view: View, day: Calendar) {
        val previousSelectedDay = calendarPageAdapter.selectedDay

        val dayLabel = view.findViewById<TextView>(R.id.dayLabel)

        if (isAnotherDaySelected(previousSelectedDay, day)) {
            selectDay(dayLabel, day)
            reverseUnselectedColor(previousSelectedDay)
            calendarPageAdapter.notifyDataSetChanged()
        }
    }

    private fun selectManyDays(view: View, day: Calendar) {
        val dayLabel = view.findViewById<TextView>(R.id.dayLabel)

        if (!day.isCurrentMonthDay() || !day.isActiveDay()) return

        val selectedDay = SelectedDay(day, dayLabel)

        if (!calendarPageAdapter.selectedDays.contains(selectedDay)) {
            setSelectedDayColors(dayLabel, day, calendarProperties)
        } else {
            reverseUnselectedColor(selectedDay)
        }

        calendarPageAdapter.addSelectedDay(selectedDay)
    }

    private fun selectRange(view: View, day: Calendar) {
        val dayLabel = view.findViewById<TextView>(R.id.dayLabel)

        if ((!day.isCurrentMonthDay() && !calendarProperties.selectionBetweenMonthsEnabled) || !day.isActiveDay()) return

        val selectedDays = calendarPageAdapter.selectedDays

        when {
            selectedDays.size > 1 -> clearAndSelectOne(dayLabel, day)
            selectedDays.size == 1 -> selectOneAndRange(dayLabel, day)
            selectedDays.isEmpty() -> selectDay(dayLabel, day)
        }
    }

    private fun selectWeeklyRange(view: View, day: Calendar) {
        val dayLabel = view.findViewById<TextView>(R.id.dayLabel)

        if ((!day.isCurrentMonthDay() && !calendarProperties.selectionBetweenMonthsEnabled) || !day.isActiveDay()) return

        clearAndWeeklyRange(dayLabel, day)
    }

    private fun clearAndSelectOne(dayLabel: TextView, day: Calendar) {
        calendarPageAdapter.selectedDays.forEach { reverseUnselectedColor(it) }
        selectDay(dayLabel, day)
        calendarPageAdapter.notifyDataSetChanged()
    }

    private fun clearAndWeeklyRange(dayLabel: TextView, day: Calendar) {
        calendarPageAdapter.selectedDays.forEach { reverseUnselectedColor(it) }
        calendarProperties.selectedDays.clear()
        day.getDatesRange(day.getEnabledDayFrom(calendarProperties,daysCount = 7))
            .filter { it !in calendarProperties.disabledDays }
            .forEach { calendarPageAdapter.addSelectedDay(SelectedDay(it)) }
        calendarPageAdapter.notifyDataSetChanged()
    }

    private fun selectOneAndRange(dayLabel: TextView, day: Calendar) {
        val previousSelectedDayCalendar = calendarPageAdapter.selectedDay.calendar

        previousSelectedDayCalendar.getDatesRange(day)
                .filter { it !in calendarProperties.disabledDays }
                .forEach { calendarPageAdapter.addSelectedDay(SelectedDay(it)) }

        if (isOutOfMaxRange(previousSelectedDayCalendar, day)) return

        setSelectedDayColors(dayLabel, day, calendarProperties)
        calendarPageAdapter.addSelectedDay(SelectedDay(day, dayLabel))
        calendarPageAdapter.notifyDataSetChanged()
    }

    private fun selectDay(dayLabel: TextView, day: Calendar) {
        setSelectedDayColors(dayLabel, day, calendarProperties)
        calendarPageAdapter.selectedDay = SelectedDay(day, dayLabel)
    }

    private fun reverseUnselectedColor(selectedDay: SelectedDay) {
        setCurrentMonthDayColors(selectedDay.calendar, selectedDay.view as? TextView, calendarProperties    )
    }

    private fun Calendar.isCurrentMonthDay() =
            this[Calendar.MONTH] == pageMonth && this.isBetweenMinAndMax(calendarProperties)

    private fun Calendar.isActiveDay() = !calendarProperties.disabledDays.contains(this)

    private fun isOutOfMaxRange(firstDay: Calendar, lastDay: Calendar): Boolean {
        // Number of selected days plus one last day
        val numberOfSelectedDays = firstDay.getDatesRange(lastDay).size + 1
        val daysMaxRange: Int = calendarProperties.maximumDaysRange

        return daysMaxRange != 0 && numberOfSelectedDays >= daysMaxRange
    }

    private fun isAnotherDaySelected(selectedDay: SelectedDay, day: Calendar) =
            day != selectedDay.calendar && day.isCurrentMonthDay() && day.isActiveDay()

    private fun onClick(day: Calendar) {
        val eventDay = when {
            calendarProperties.eventDays.isEmpty() -> EventDay(day)
            else -> calendarProperties.eventDays.firstOrNull { it.calendar == day }
        }
        callOnClickListener(eventDay ?: EventDay(day))
    }


    private fun callOnClickListener(eventDay: EventDay) {
        val enabledDay = calendarProperties.disabledDays.contains(eventDay.calendar) || eventDay.calendar.isBetweenMinAndMax(calendarProperties)
        eventDay.isEnabled = enabledDay
        calendarProperties.onCalendarItemClickListener?.onCalendarItemClick(eventDay,calendarProperties.calendarType)
    }
}