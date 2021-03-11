package com.example.library.customCalenderView.utils

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.View
import com.example.library.R
import com.example.library.customCalenderView.CalendarDay
import com.example.library.customCalenderView.EventDay
import com.example.library.customCalenderView.exceptions.ErrorsMessages
import com.example.library.customCalenderView.exceptions.UnsupportedMethodsException
import com.example.library.customCalenderView.CalenderView
import com.example.library.customCalenderView.CalenderView.Companion.ONE_DAY_PICKER
import com.example.library.customCalenderView.CalenderView.Companion.VIEW_TYPE_DAY
import com.example.library.customCalenderView.listeners.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * This class contains all properties of the calendar
 *
 * Created by Himanshu.
 */

typealias OnPagePrepareListener = (Calendar) -> List<CalendarDay>

class CalendarProperties(private val context: Context) {

    //tells if calendar is one_day_picker,many_day_picker or range_picker
    var calendarType: Int = ONE_DAY_PICKER

    //tells if it is day or month view type
    var calendarViewType: Int = VIEW_TYPE_DAY

    var headerColor: Int = 0
        get() = if (field <= 0) field else context.parseColor(field)

    var headerLabelColor: Int = 0
        get() = if (field <= 0) field else context.parseColor(field)

    var selectionColor: Int = 0
        get() = if (field == 0) context.parseColor(R.color.light_grey_2) else field

    var todayLabelColor: Int = 0
        get() = if (field == 0) context.parseColor(R.color.daysLabelColor) else field

    var todayColor: Int = 0
        get() = if (field <= 0) field else context.parseColor(field)

    var todayBackgroundRes: Int = R.drawable.bg_rect_outline_grey

    var dialogButtonsColor: Int = 0

    var itemLayoutResource: Int = R.layout.calendar_view_day

    var itemMonthLayoutResource: Int = R.layout.calendar_month_view

    var selectionBackground: Int = R.drawable.background_color_rect_selector

    var disabledDaysLabelsColor: Int = 0
        get() = if (field == 0) context.parseColor(R.color.nextMonthDayColor) else field

    var highlightedDaysLabelsColor: Int = 0
        get() = if (field == 0) context.parseColor(R.color.nextMonthDayColor) else field

    var pagesColor: Int = 0

    var abbreviationsBarColor: Int = 0

    var abbreviationsLabelsColor: Int = 0

    var daysLabelsColor: Int = 0
        get() = if (field == 0) context.parseColor(R.color.currentMonthDayColor) else field

    var selectionLabelColor: Int = 0
        get() = if (field == 0) context.parseColor(R.color.colorPrimary) else field

    var anotherMonthsDaysLabelsColor: Int = 0
        get() = if (field == 0) context.parseColor(R.color.nextMonthDayColor) else field

    var headerVisibility: Int = View.VISIBLE

    var typeface: Typeface? = null

    var todayTypeface: Typeface? = null

    var abbreviationsBarVisibility: Int = View.VISIBLE

    var navigationVisibility: Int = View.VISIBLE

    var eventsEnabled: Boolean = false

    var swipeEnabled: Boolean = true

    var calenderSelectionTypeEnabled: Boolean = true

    var selectionDisabled: Boolean = false

    var previousButtonSrc: Drawable? = null

    var forwardButtonSrc: Drawable? = null

    var selectionBetweenMonthsEnabled: Boolean = false

    val firstPageCalendarDate: Calendar = midnightCalendar

    var firstDayOfWeek = firstPageCalendarDate.firstDayOfWeek

    var calendar: Calendar? = null

    var minimumDate: Calendar? = null

    var maximumDate: Calendar? = null

    var maximumDaysRange: Int = 0

    var onCalendarItemClickListener: OnCalendarItemClickListener? = null

    var onDayLongClickListener: OnDayLongClickListener? = null

    var onSelectDateListener: OnSelectDateListener? = null

    var onSelectionAbilityListener: OnSelectionAbilityListener? = null

    var onPreviousPageChangeListener: OnCalendarPageChangeListener? = null

    var onForwardPageChangeListener: OnCalendarPageChangeListener? = null

    var eventDays: List<EventDay> = mutableListOf()

    var calendarDayProperties: MutableList<CalendarDay> = mutableListOf()

    var disabledDays: List<Calendar> = mutableListOf()
        set(disabledDays) {
            selectedDays = selectedDays.filter {
                !disabledDays.contains(it.calendar)
            }.toMutableList()

            field = disabledDays.map { it.setMidnight() }.toList()
        }

    var disabledMonths: List<Calendar> = mutableListOf()
        set(disabledMonths) {
            selectedMonths = selectedMonths.filter {
                !disabledMonths.contains(it.calendar)
            }.toMutableList()

            field = disabledMonths.map { it.setMidnight() }.toList()
        }

    var highlightedDays: List<Calendar> = mutableListOf()
        set(highlightedDays) {
            field = highlightedDays.map { it.setMidnight() }.toList()
        }

    var selectedDays = mutableListOf<SelectedDay>()
        private set

    var selectedMonths = mutableListOf<SelectedMonth>()
        private set

    fun setSelectedDay(calendar: Calendar) = setSelectedDay(SelectedDay(calendar))

    fun setSelectedDay(selectedDay: SelectedDay) {
        selectedDays.clear()
        selectedDays.add(selectedDay)
    }

    fun setSelectedMonth(calendar: Calendar) = setSelectedMonth(SelectedMonth(calendar))

    fun setSelectedMonth(selectedMonth: SelectedMonth) {
        selectedMonths.clear()
        selectedMonths.add(selectedMonth)
    }

    @Throws(UnsupportedMethodsException::class)
    fun setSelectDays(days: List<Calendar>) {
        if (calendarType == CalenderView.ONE_DAY_PICKER) {
            throw UnsupportedMethodsException(ErrorsMessages.ONE_DAY_PICKER_MULTIPLE_SELECTION)
        }

        if ((calendarType == CalenderView.RANGE_PICKER || calendarType == CalenderView.WEEKLY_RANGE_PICKER) && !days.isFullDatesRange()) {
            throw UnsupportedMethodsException(ErrorsMessages.RANGE_PICKER_NOT_RANGE)
        }

        selectedDays = days
                .map { SelectedDay(it.setMidnight()) }
                .filterNot { it.calendar in disabledDays }
                .toMutableList()
    }

    var onPagePrepareListener: OnPagePrepareListener? = null

    fun findDayProperties(calendar: Calendar): CalendarDay? =
            calendarDayProperties.find { it.calendar.isEqual(calendar) }

    companion object {
        /**
         * A number of months (pages) in the calendar
         * 2401 months means 1200 months (100 years) before and 1200 months after the current month
         */
        const val CALENDAR_SIZE = 2401
        const val CALENDAR_YEAR_SIZE = 100
        const val FIRST_VISIBLE_PAGE = CALENDAR_SIZE / 2
        const val FIRST_VISIBLE_PAGE_MONTH = CALENDAR_YEAR_SIZE / 2
        const val MAX_CELLS_COUNT_MONTH = 42
        const val MAX_CELLS_COUNT_YEAR = 12

        val monthHeaderList : ArrayList<String> = arrayListOf("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec")
    }
}
