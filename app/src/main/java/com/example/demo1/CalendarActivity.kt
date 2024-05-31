package com.example.demo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.CalendarView
import java.util.Calendar

class CalendarActivity : AppCompatActivity() {
    // on below line we are creating
    // variables for text view and calendar view

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
        supportActionBar?.hide()

        val calendarView: CalendarView = findViewById(R.id.calView)
        val currentDate = Calendar.getInstance()

        val ListButton : Button = findViewById(R.id.listBtn)

        // Set calendarView clickable and focusable
        calendarView.isClickable = true
        calendarView.isFocusable = true

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)

            if (selectedDate.get(Calendar.YEAR) == currentDate.get(Calendar.YEAR) &&
                selectedDate.get(Calendar.MONTH) == currentDate.get(Calendar.MONTH) &&
                selectedDate.get(Calendar.DAY_OF_MONTH) == currentDate.get(Calendar.DAY_OF_MONTH)) {
                // Create intent to start MainActivity2 when a date is selected
                val intent = Intent(this@CalendarActivity, EventList::class.java)
                startActivity(intent)
            } else {
                // Optionally, you can provide feedback to the user that they need to select the current date.
                // You can show a Toast or set some text on a TextView.
            }
        }

        ListButton.setOnClickListener() {
            val listIntent = Intent(this, EventList::class.java)
            //start activity
            startActivity(listIntent)
        }
    }
}

/*override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_calendar)

    // initializing variables of
    // list view with their ids.
    dateTV = findViewById(R.id.idTVDate)
    calendarView = findViewById(R.id.calendarView)

    // on below line we are adding set on
    // date change listener for calendar view.
    calendarView
        .setOnDateChangeListener(
            OnDateChangeListener { view, year, month, dayOfMonth ->
                // In this Listener we are getting values
                // such as year, month and day of month
                // on below line we are creating a variable
                // in which we are adding all the variables in it.
                val Date = (dayOfMonth.toString() + "-"
                        + (month + 1) + "-" + year)

                // set this date in TextView for Display
                dateTV.setText(Date)
            })

}*/