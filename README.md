# Cali
How you want your Calendar to look like

**How you want your calendar to look like**

<p align="center">
  <img src="https://user-images.githubusercontent.com/20729864/110770402-4a10bb00-827f-11eb-816b-9d346aeccdca.jpg" width="150" height="300"> 
  <img src="https://user-images.githubusercontent.com/20729864/110771800-ed160480-8280-11eb-961a-5d94031e50b3.jpg" width="150" height="300">
  <img src="https://user-images.githubusercontent.com/20729864/110770385-467d3400-827f-11eb-891e-d257d695b9ce.jpg" width="150" height="300">
  <img src="https://user-images.githubusercontent.com/20729864/110770396-4846f780-827f-11eb-8528-beb18f46408f.jpg" width="150" height="300">
  <img src="https://user-images.githubusercontent.com/20729864/110770399-48df8e00-827f-11eb-8975-6bd26e2c9c3a.jpg" width="150" height="300">  
</p>

## Features

- [x] [Day/Month View) - Library provides the option to show either month or day view (using calendarViewType)
- [x] [One/Range/Many Day Select] - If calendarViewType is day then library provides the way to select one or many days, also in range. 
- [x] [Disable desired dates] - Prevent selection of some dates by disabling them.
- [x] [Daily/Weekly/Monthly Modes] - You can select mode how you want to select days in the same view.
- [x] Custom date view - make your day cells look however you want.
- [x] [Custom first day of the week] - Use any day as the first day of the week.
- [x] Enable/Disable the swiping behaviour for the calendar.
- [x] [Month/Year headers] - Add headers/footers of any kind on each month.

#### Steps to use in your project

Add it in your root build.gradle at the end of repositories:

```groovy
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
}
```

Add the dependency

```groovy
dependencies {
	  implementation 'com.github.himanshu24yadav:cali:<latest_version>'
}
```

You can find the latest version of Calendar View in the releases.


#### How to add CalendarView

Add Calendar to your XML 

```xml
<com.himanshu24yadav.cali.calendarView.CalenderView
        android:id="@+id/caliView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:calenderSelectionTypeEnabled="true"
	app:calendarViewType="month"
	app:type="one_day_picker"					    
        app:swipeEnabled="false"
         />
```

Add Click Listener

```groovy

//Kotlin
calendarViewOTDF.setOnItemClickListener(this)
override fun onCalendarItemClick(eventDay: EventDay, calendarType: Int?) {
	//calenderTypes : CLASSIC,ONE_DAY_PICKER,MANY_DAYS_PICKER,RANGE_PICKER,WEEKLY_RANGE_PICKER,MONTH_PICKER
	//EventDay : Will provide calender instance as well tells if the selected day is enabled/disabled
}

```

### Attributes

All prefixed `hy_` for ease

- **hy_headerColor**: The xml resource that is inflated and used as the month/year header color.

- **hy_headerLabelColor**: The xml resource that is inflated and used as the month/year headerLabel color.
 
- **hy_previousButtonSrc/hy_forwardButtonSrc**: The xml resource that is inflated and used as previous/forward button for the calendar.

- **hy_todayLabelColor/hy_daysLabelsColor/hy_anotherMonthsDaysLabelsColor/hy_selectionLabelColor/hy_disabledDaysLabelsColor/hy_highlightedDaysLabelsColor**: The xml resource that is inflated and used as label colors for the text for different dates type.

- **hy_swipeEnabled**: The xml resource that is inflated and used as to enable/disable swipe between Months/Year. Default is enabled

- **hy_selectionDisabled**: The xml resource that is inflated and used as enable/disable clicks of Months/Day. Default is enabled

- **hy_selectionBetweenMonthsEnabled**: The xml resource that is inflated and used as to select days between months when multiple days can be selected. This must be provided.

- **hy_maximumDaysRange**: The xml resource that is inflated and used as to set maximum days range in case type is MANY_DAY/RANGE_PICKER.

- **hy_typeface**: The xml resource that is inflated and used as typeface for the day/month view.


**hy_calendarViewType Vs hy_type**

`hy_calendarViewType` : Possible values month/day. Used as to show either month or day view

`hy_type` : Possible values classic/one_day_picker/many_days_picker/range_picker . If provided this then hy_calendarViewType will be day and selection option will be selected as per provided in this attribute.


### Methods

- **setOnItemClickListener** : OnDayClickListener interface responsible for handle clicks on calendar cells
- **setOnDayLongClickListener** : OnDayClickListener interface responsible for handle long clicks on calendar cells
- **setDate** : This method set a current date of the calendar using Calendar object.
- **selectedDates** : List of Calendar objects representing a selected dates
- **selectedDate** : Calendar object representing a selected date
- **setMinimumDate** : This method set a minimum available date in calendar
- **showCurrentMonthPage** : This method is used to return to current month page
- **setDisabledDays** : This method sets all disabled days of calendar
- **setSwipeEnabled** : This method enable/disable swipe
- **setSelctionTypeEnabled** : This method show/hide selection view day/weekly/monthly
- **setMaximumDate** : This method set a maximum available date in calendar


## Inspiration

Cali was inspired by the Android library (https://github.com/kizitonwose/CalendarView/)



Found a bug? feel free to fix it and send a pull request or [open an issue](https://github.com/himanshu24yadav/cali/issues).
