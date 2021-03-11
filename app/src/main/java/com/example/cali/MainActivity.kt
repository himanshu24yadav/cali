package com.example.cali

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvMonthView.setOnClickListener {
            groupBtns.visibility = View.GONE
            monthCal.visibility = View.VISIBLE
            tvBack.visibility = View.VISIBLE
        }

        tvDayView.setOnClickListener {
            groupBtns.visibility = View.GONE
            dayCal.visibility = View.VISIBLE
            tvBack.visibility = View.VISIBLE
        }

        tvSelectionTypeView.setOnClickListener {
            groupBtns.visibility = View.GONE
            selectionTypeCal.visibility = View.VISIBLE
            tvBack.visibility = View.VISIBLE
        }

        tvBack.setOnClickListener {
            groupBtns.visibility = View.VISIBLE
            selectionTypeCal.visibility = View.GONE
            dayCal.visibility = View.GONE
            monthCal.visibility = View.GONE
            tvBack.visibility = View.GONE
        }
    }
}