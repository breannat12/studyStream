package com.example.demo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val HomeButton : Button = findViewById(R.id.welcomeBtn)
        supportActionBar?.hide()

        HomeButton.setOnClickListener() {
            val homeIntent = Intent(this, Home::class.java)
            //start activity
            startActivity(homeIntent)
        }
    }
}