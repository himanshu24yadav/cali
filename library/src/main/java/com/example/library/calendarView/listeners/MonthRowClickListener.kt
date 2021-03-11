package com.example.library.calendarView.listeners

import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import com.example.library.R
import com.example.library.calendarView.CalenderView
import com.example.library.calendarView.EventDay
import com.example.library.calendarView.utils.*
import com.example.library.calendarView.utils.setCurrentMonthDayColors
import com.example.library.calendarView.utils.setSelectedDayColors
import com.example.library.calendarView.adapters.MonthCalendarPageAdapter
import java.util.*

/**
 * This class is responsible for handle click events
 *
 *
 * Created by Himanshu.
 */
class MonthRowClickListener(private val monthCalendarPageAdapter: MonthCalendarPageAdapter, private val calendarProperties: CalendarProperties, pageYear: Int) : AdapterView.OnItemClickListener {

    private val pageYear = if (pageYear < 0) Calendar.getInstance().get(Calendar.YEAR) else pageYear

    override fun onItemClick(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
        val month = GregorianCalendar().apply {
            time = adapterView.getItemAtPosition(position) as Date
        }

        if (calendarProperties.selectionDisabled) return

        when (calendarProperties.calendarType) {
            CalenderView.MONTH_PICKER -> selectOneMonth(view, month)
        }

        if (calendarProperties.onCalendarItemClickListener != null) {
            onClick(month)
        }
    }

    private fun selectOneMonth(view: View, day: Calendar) {
        val previousSelectedMonth = monthCalendarPageAdapter.selectedMonth

        val monthLabel = view.findViewById<TextView>(R.id.monthLabel)

        if (isAnotherMonthSelected(previousSelectedMonth, day)) {
            selectMonth(monthLabel, day)
            reverseUnselectedColor(previousSelectedMonth)
            monthCalendarPageAdapter.notifyDataSetChanged()
        }
    }

    private fun selectMonth(monthLabel: TextView, day: Calendar) {
        setSelectedDayColors(monthLabel, day, calendarProperties)
        monthCalendarPageAdapter.selectedMonth = SelectedMonth(day, monthLabel)
    }

    private fun reverseUnselectedColor(selectedMonth: SelectedMonth) {
        setCurrentMonthDayColors(selectedMonth.calendar, selectedMonth.view as? TextView, calendarProperties)
    }

    private fun Calendar.isCurrentYear() = this[Calendar.YEAR] == pageYear

    private fun Calendar.isActiveMonth() = this.isBetweenMinAndMax(calendarProperties)

    private fun isAnotherMonthSelected(selectedMonth: SelectedMonth, day: Calendar) =
        day != selectedMonth.calendar && day.isCurrentYear() && day.isActiveMonth()

    private fun onClick(day: Calendar) {
        val eventDay = when {
            calendarProperties.eventDays.isEmpty() -> EventDay(day)
            else -> calendarProperties.eventDays.firstOrNull { it.calendar == day }
        }
        callOnClickListener(eventDay ?: EventDay(day))
    }


    private fun callOnClickListener(eventDay: EventDay) {
        val enabledDay = calendarProperties.disabledMonths.contains(eventDay.calendar) || eventDay.calendar.isBetweenMinAndMax(calendarProperties)
        eventDay.isEnabled = enabledDay
        calendarProperties.onCalendarItemClickListener?.onCalendarItemClick(eventDay,calendarProperties.calendarType)
    }
}