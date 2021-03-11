package com.example.library.customCalenderView.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.example.library.R
import com.example.library.customCalenderView.extensions.CalendarGridView
import com.example.library.customCalenderView.listeners.MonthRowClickListener
import com.example.library.customCalenderView.utils.CalendarProperties
import com.example.library.customCalenderView.utils.SelectedMonth
import java.util.*

/**
 * This class is responsible for loading a calendar page content.
 *
 *
 * Created by Himanshu
 */
class MonthCalendarPageAdapter(private val context: Context, private val calendarProperties: CalendarProperties) : PagerAdapter() {

    private lateinit var calendarGridView: CalendarGridView

    private var pageYear = 0

    val selectedMonths: List<SelectedMonth>
        get() = calendarProperties.selectedMonths

    var selectedMonth: SelectedMonth
        get() = calendarProperties.selectedMonths.first()
        set(selectedMonth) {
            calendarProperties.setSelectedMonth(selectedMonth)
        }

    override fun getCount() = CalendarProperties.CALENDAR_YEAR_SIZE

    override fun getItemPosition(any: Any) = POSITION_NONE

    override fun isViewFromObject(view: View, any: Any) = view === any

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        calendarGridView = View.inflate(context, R.layout.calendar_view_grid, null) as CalendarGridView
        calendarGridView.numColumns = 3

        loadYear(position)

        calendarGridView.onItemClickListener = MonthRowClickListener(this, calendarProperties, pageYear)

        container.addView(calendarGridView)

        return calendarGridView
    }

    /**
     * This method fill calendar GridView with months
     *
     * @param position Position of current page in ViewPager
     */
    private fun loadYear(position: Int) {
        val months = mutableListOf<Date>()

        // Get Calendar object instance
        val calendar = (calendarProperties.firstPageCalendarDate.clone() as Calendar).apply {
            // Add years to Calendar (a number of years depends on ViewPager position)
            add(Calendar.YEAR, position)

            // Set day of year as 1
            set(Calendar.DAY_OF_YEAR, 1)
        }

        getPageMonthsProperties(calendar)

        /*
        Get all months of one page (12 is a number of all possible cells in one page
        */
        while (months.size < CalendarProperties.MAX_CELLS_COUNT_YEAR) {
            months.add(calendar.time)
            calendar.add(Calendar.MONTH, 1)
        }

        pageYear = calendar.get(Calendar.YEAR) - 1
        val calendarMonthAdapter = CalendarMonthAdapter(context, this, calendarProperties, months, pageYear)

        calendarGridView.adapter = calendarMonthAdapter
    }

    private fun getPageMonthsProperties(calendar: Calendar) {
        val pageCalendarDays = calendarProperties.onPagePrepareListener?.invoke(calendar)
        if (pageCalendarDays != null) {
            val diff = pageCalendarDays.minus(calendarProperties.calendarDayProperties).distinct()
            calendarProperties.calendarDayProperties.addAll(diff)
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
        container.removeView(any as View)
    }
}