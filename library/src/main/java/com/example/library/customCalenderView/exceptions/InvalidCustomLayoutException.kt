package com.example.library.customCalenderView.exceptions

object InvalidCustomLayoutException : RuntimeException("YOUR CUSTOM CALENDAR DAY LAYOUT MUST CONTAIN A TextView WITH android:id=\"@+id/dayLabel\"")