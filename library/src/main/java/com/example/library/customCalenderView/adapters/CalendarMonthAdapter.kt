package com.example.library.customCalenderView.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.library.customCalenderView.CalenderView
import com.example.library.customCalenderView.exceptions.InvalidCustomLayoutException
import com.example.library.customCalenderView.getMonthAsText
import com.example.library.customCalenderView.utils.*
import com.example.library.customCalenderView.utils.isEventDayWithLabelColor
import com.example.library.customCalenderView.utils.setCurrentMonthDayColors
import com.example.library.customCalenderView.utils.setSelectedDayColors
import kotlinx.android.synthetic.main.calendar_month_view.view.*
import java.util.*

/**
 * This class is responsible for loading a one day cell.
 *
 *
 * Created by Himanshu
 */
class CalendarMonthAdapter(context: Context, private val monthCalendarPageAdapter: MonthCalendarPageAdapter, private val calendarProperties: CalendarProperties, dates: MutableList<Date>, pageYear: Int)
    : ArrayAdapter<Date>(context, calendarProperties.itemMonthLayoutResource, dates) {

    private val pageYear = if (pageYear < 0) Calendar.getInstance().get(Calendar.YEAR) else pageYear

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val monthView = view ?: LayoutInflater.from(context).inflate(calendarProperties.itemMonthLayoutResource, parent, false)

        val month = GregorianCalendar().apply { time = getItem(position) ?: calendarProperties.calendar?.time ?: Date() }

        val monthLabel = monthView.monthLabel ?: throw InvalidCustomLayoutException

        setLabelColors(monthLabel, month)

        monthLabel.text = month.getMonthAsText()

        return monthView
    }

    private fun setLabelColors(monthLabel: TextView, day: Calendar) {
        when {
            // Setting not current month day color
            !day.isCurrentMonth() ->
                monthLabel.setDayColors(calendarProperties.anotherMonthsDaysLabelsColor)

            // Setting view for all SelectedMonths
            day.isSelectedMonth() -> {
                monthCalendarPageAdapter.selectedMonths.firstOrNull { selectedMonth -> selectedMonth.calendar == day }?.let { selectedMonth -> selectedMonth.view = monthLabel }
                setSelectedDayColors(monthLabel, day, calendarProperties)
            }

            // Setting not current month day color only if selection between months is enabled for range picker
            !day.isCurrentMonth() && calendarProperties.selectionBetweenMonthsEnabled -> {
                if (SelectedMonth(day) !in monthCalendarPageAdapter.selectedMonths) {
                    monthLabel.setDayColors(calendarProperties.anotherMonthsDaysLabelsColor)
                }
            }

            // Setting disabled days color
            !day.isActiveMonth() -> monthLabel.setDayColors(calendarProperties.disabledDaysLabelsColor)

            // Setting custom label color for event day
            day.isEventMonthWithLabelColor() -> setCurrentMonthDayColors(day, monthLabel, calendarProperties)

            // Setting current month day color
            else -> setCurrentMonthDayColors(day, monthLabel, calendarProperties)
        }
    }

    private fun Calendar.isSelectedMonth() = calendarProperties.calendarType != CalenderView.CLASSIC
            && SelectedMonth(this) in monthCalendarPageAdapter.selectedMonths
            && this[Calendar.YEAR] == pageYear

    private fun Calendar.isEventMonthWithLabelColor() = this.isEventDayWithLabelColor(calendarProperties)

    private fun Calendar.isCurrentMonth() = this[Calendar.YEAR] == pageYear && !(calendarProperties.minimumDate != null
            && this.before(calendarProperties.minimumDate)
            || calendarProperties.maximumDate != null
            && this.after(calendarProperties.maximumDate))

    private fun Calendar.isActiveMonth() = this !in calendarProperties.disabledDays
}