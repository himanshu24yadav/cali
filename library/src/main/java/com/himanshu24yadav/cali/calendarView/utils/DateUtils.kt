@file:JvmName("DateUtils")

package com.himanshu24yadav.cali.calendarView.utils

import android.content.Context
import com.example.library.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @return An instance of the Calendar object with hour set to 00:00:00:00
 */
val midnightCalendar: Calendar
    get() = Calendar.getInstance().apply {
        this.setMidnight()
    }

@Deprecated("Use getMidnightCalendar()", ReplaceWith("Calendar.getInstance().apply { this.setMidnight() }", "java.util.Calendar"))
val calendar: Calendar
    get() = Calendar.getInstance().apply {
        this.setMidnight()
    }

/**
 * This method sets an hour in the calendar object to 00:00:00:00
 *
 * @param this Calendar object which hour should be set to 00:00:00:00
 */
fun Calendar.setMidnight() = this.apply {
    set(Calendar.HOUR_OF_DAY, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
}

/**
 * This method compares calendars using month and year
 *
 * @param this  First calendar object to compare
 * @param secondCalendar Second calendar object to compare
 * @return Boolean value if second calendar is before the first one
 */
fun Calendar?.isMonthBefore(secondCalendar: Calendar?): Boolean {
    if (this == null || secondCalendar == null) return false

    val firstDay = (this.clone() as Calendar).apply {
        setMidnight()
        set(Calendar.DAY_OF_MONTH, 1)
    }
    val secondDay = (secondCalendar.clone() as Calendar).apply {
        setMidnight()
        set(Calendar.DAY_OF_MONTH, 1)
    }

    return secondDay.before(firstDay)
}

/**
 * This method compares calendars using month and year
 *
 * @param this  First calendar object to compare
 * @param secondCalendar Second calendar object to compare
 * @return Boolean value if second calendar is after the first one
 */
fun Calendar?.isMonthAfter(secondCalendar: Calendar) = secondCalendar.isMonthBefore(this)

/**
 * This method returns a string containing a month's name and a year (in number).
 * It's used instead of new SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format([Date]);
 * because that method returns a month's name in incorrect form in some languages (i.e. in Polish)
 *
 * @param this A Calendar object containing date which will be formatted
 * @param context  An array of months names
 * @return A string of the formatted date containing a month's name and a year (in number)
 */
internal fun Calendar.getMonthAndYearDate(context: Context) = String.format(
        "%s  %s",
        context.resources.getStringArray(R.array.material_calendar_months_array)[this.get(Calendar.MONTH)],
        this.get(Calendar.YEAR)
)

/**
 * This method returns a string containing a month's name and a year (in number).
 * It's used instead of new SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format([Date]);
 * because that method returns a month's name in incorrect form in some languages (i.e. in Polish)
 *
 * @param this A Calendar object containing date which will be formatted
 * @return A string of the formatted date containing a month's name and a year (in number)
 */
internal fun Calendar.getYearDate() = this.get(Calendar.YEAR).toString()

/**
 * This method is used to count a number of months between two dates
 *
 * @param this Calendar representing a first date
 * @param endCalendar   Calendar representing a last date
 * @return Number of months
 */
fun Calendar.getMonthsToDate(endCalendar: Calendar): Int {
    val years = endCalendar.get(Calendar.YEAR) - this.get(Calendar.YEAR)
    return years * 12 + endCalendar.get(Calendar.MONTH) - this.get(Calendar.MONTH)
}

/**
 * This method checks whether date is correctly between min and max date or not
 *
 * @param calendarProperties Calendar properties
 */
fun Calendar.isBetweenMinAndMax(calendarProperties: CalendarProperties) =
        !(calendarProperties.minimumDate != null && this.before(calendarProperties.minimumDate)
                || calendarProperties.maximumDate != null && this.after(calendarProperties.maximumDate))

/**
 * This method is used to count a number of days between two dates
 *
 * @param this Calendar representing a first date
 * @param endCalendar   Calendar representing a last date
 * @return Number of days
 *
 * +1 is necessary because method counts from the beginning of start day to beginning of end day
 * and 1, means whole end day
 */
private fun Calendar.getDaysToDate(endCalendar: Calendar) =
        TimeUnit.MILLISECONDS.toDays(endCalendar.timeInMillis - this.timeInMillis) + 1

internal fun List<Calendar>.isFullDatesRange(): Boolean {
    val selectedDates = this.distinct().sortedBy { it.timeInMillis }

    if (this.isEmpty() || selectedDates.size == 1) return true

    return selectedDates.size.toLong() == selectedDates.first().getDaysToDate(selectedDates.last())
}

val Calendar.isToday
    get() = this == midnightCalendar

fun Calendar.isEqual(calendar: Calendar) = this.setMidnight() == calendar.setMidnight()

fun Calendar.isSameMonth(calendar: Calendar? = null) : Boolean {
    return calendar?.let {
        if(this.get(Calendar.YEAR) == it.get(Calendar.YEAR)) {
            this.get(Calendar.MONTH) == it.get(Calendar.MONTH)
        } else {
            false
        }
    } ?: false
}

fun Date?.getFirstDayOfMonth(dateFormat: String): String {
    return this?.let { date ->
        var convertedDate = ""
        try {
            val formatter: DateFormat = SimpleDateFormat(dateFormat, Locale.getDefault())
            val cal = Calendar.getInstance()
            cal.time = date
            cal.set(Calendar.DAY_OF_MONTH, 1)
            convertedDate = formatter.format(cal.time)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        convertedDate
    } ?: ""
}

fun Date?.getLastDayOfMonth(dateFormat: String): String {
    return this?.let { date ->
        var convertedDate = ""
        try {
            val formatter: DateFormat = SimpleDateFormat(dateFormat, Locale.getDefault())
            val cal = Calendar.getInstance()
            cal.time = date
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH))
            convertedDate = formatter.format(cal.time)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        convertedDate
    } ?: ""
}