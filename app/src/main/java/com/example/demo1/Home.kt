package com.example.demo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide()

        val calendarButton : Button = findViewById(R.id.calButton)
        val timerButton : Button = findViewById(R.id.timerBtn)
        val musicButton : Button = findViewById(R.id.musicBtn)

        calendarButton.setOnClickListener {
            //open other view; create intent
            val calendarIntent = Intent(this, CalendarActivity::class.java)
            //start activity
            startActivity(calendarIntent)
        }

        timerButton.setOnClickListener {
            //open other view; create intent
            val timerIntent = Intent(this, TimerActivity::class.java)
            //start activity
            startActivity(timerIntent)
        }

        musicButton.setOnClickListener {
            //open other view; create intent
            val musicIntent = Intent(this, MusicActivity::class.java)
            //start activity
            startActivity(musicIntent)
        }
    }
}