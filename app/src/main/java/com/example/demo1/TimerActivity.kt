package com.example.demo1


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class TimerActivity : AppCompatActivity() {


    private lateinit var textViewCountdown: TextView
    private lateinit var editTextTime: EditText
    private lateinit var countDownTimer: CountDownTimer
    private var timeLeftInMillis: Long = 0
    private var timerRunning = false
    private val countdownInterval: Long = 1000
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var textViewSavedTime: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
        supportActionBar?.hide()

        val homeBtn: ImageView = findViewById(R.id.homeBtn)

        homeBtn.setOnClickListener {
            // Open other view; create intent
            val taskIntent = Intent(this, Home::class.java)
            // Start activity
            startActivity(taskIntent)
        }


        textViewCountdown = findViewById(R.id.textViewCountdown)
        editTextTime = findViewById(R.id.editTextTime)


        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("timer_data", Context.MODE_PRIVATE)


        // Retrieve the saved time if available
        timeLeftInMillis = sharedPreferences.getLong("time_left_in_millis", 0)
        timerRunning = sharedPreferences.getBoolean("timer_running", false)


        if (timerRunning) {
            startTimer(null) // Start the timer if it was running before
        } else {
            updateCountDownText() // Update the text if timer was paused or not started
        }


        // Show the saved time if available
        if (timeLeftInMillis > 0) {
            textViewSavedTime.visibility = View.VISIBLE
            val savedMinutes = (timeLeftInMillis / 1000) / 60
            val savedSeconds = (timeLeftInMillis / 1000) % 60
            val savedTimeFormatted = String.format("%02d:%02d", savedMinutes, savedSeconds)
            textViewSavedTime.text = "Saved Time: $savedTimeFormatted"
        }
    }


    override fun onStop() {
        super.onStop()
        // Save the current timer state
        with(sharedPreferences.edit()) {
            putLong("time_left_in_millis", timeLeftInMillis)
            putBoolean("timer_running", timerRunning)
            apply()
        }
    }


    fun startTimer(view: View?) {
        if (timerRunning) {
            return
        }


        val inputTimeMinutes = editTextTime.text.toString().toLongOrNull()


        if (inputTimeMinutes != null && inputTimeMinutes > 0) {
            val timeInMillis = inputTimeMinutes * 60 * 1000
            countDownTimer = object : CountDownTimer(timeInMillis, countdownInterval) {
                override fun onTick(millisUntilFinished: Long) {
                    timeLeftInMillis = millisUntilFinished
                    updateCountDownText()
                }


                override fun onFinish() {
                    textViewCountdown.text = "Timer finished"
                    timerRunning = false
                }
            }.start()


            timerRunning = true
        } else {
            Toast.makeText(this, "Please enter a valid time in minutes", Toast.LENGTH_SHORT).show()
        }
    }


    fun stopTimer(view: View?) {
        countDownTimer.cancel()
        timerRunning = false
    }


    fun resetTimer(view: View?) {
        if (timerRunning) {
            countDownTimer.cancel()
            timerRunning = false
        }
        timeLeftInMillis = 0
        textViewCountdown.text = "Countdown Timer"
        editTextTime.text.clear()
    }


    private fun updateCountDownText() {
        val minutes = (timeLeftInMillis / 1000) / 60
        val seconds = (timeLeftInMillis / 1000) % 60
        val timeLeftFormatted = String.format("%02d:%02d", minutes, seconds)
        textViewCountdown.text = timeLeftFormatted
    }


    fun resumeTimer(view: View?) {
        if (timeLeftInMillis > 0 && !timerRunning) {
            countDownTimer = object : CountDownTimer(timeLeftInMillis, countdownInterval) {
                override fun onTick(millisUntilFinished: Long) {
                    timeLeftInMillis = millisUntilFinished
                    updateCountDownText()
                }


                override fun onFinish() {
                    textViewCountdown.text = "Timer finished"
                    timerRunning = false
                }
            }.start()
            timerRunning = true
        }
    }
}
