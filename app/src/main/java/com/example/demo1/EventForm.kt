package com.example.demo1

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import android.widget.Button
import android.widget.CheckBox
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream

class EventForm : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_form)

        val addButton: Button = findViewById(R.id.addButton)

        val todayBtn: CheckBox = findViewById(R.id.todayCheck)
        val tmrBtn: CheckBox = findViewById(R.id.tmrCheck)
        val laterBtn: CheckBox = findViewById(R.id.laterCheck)
        val todayColor = "#FE7488"
        val tmrColor = "#FFB074"
        val ltrColor = "#EDDA5D"

        val titleInputLayout = findViewById<TextInputLayout>(R.id.titleInput)
        val descriptionInputLayout = findViewById<TextInputLayout>(R.id.descriptionInput)

        var selectedColor = 0 // Default color

        todayBtn.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                selectedColor =
                    Color.parseColor(todayColor) // Example color, you can set this to your desired color
            }
        }

        tmrBtn.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                selectedColor =
                    Color.parseColor(tmrColor) // Example color, you can set this to your desired color
            }
        }

        laterBtn.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                selectedColor =
                    Color.parseColor(ltrColor) // Example color, you can set this to your desired color
            }
        }

        addButton.setOnClickListener {
            val title = titleInputLayout.editText?.text.toString()
            val description = descriptionInputLayout.editText?.text.toString()

            // Save the task details, deadline, and color to a cache file
            saveTaskToCacheFile(title, description, selectedColor)

            // Pass the event details and selected color to EventList activity
            val intent = Intent(this, EventList::class.java).apply {
                putExtra("title", title)
                putExtra("description", description)
                putExtra("color", selectedColor)
            }
            startActivity(intent)
        }

    }

    private fun saveTaskToCacheFile(title: String, description: String, color: Int) {
        val cacheDir = cacheDir // Directory to store cache files
        val cacheFile = File(cacheDir, "events")

        // Check if the file exists, create it if it doesn't
        if (!cacheFile.exists()) {
            cacheFile.createNewFile()
        }

        // Append the task data to the existing file
        cacheFile.appendText("$title,$description,$color\n")
    }
}

/*
class EventForm : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_form)

        val addButton: Button = findViewById(R.id.addButton)

        val todayBtn: CheckBox = findViewById(R.id.todayCheck)
        val tmrBtn: CheckBox = findViewById(R.id.tmrCheck)
        val laterBtn: CheckBox = findViewById(R.id.laterCheck)
        val todayColor = "#FE7488"
        val tmrColor = "#FFB074"
        val ltrColor = "#EDDA5D"

        val titleInputLayout = findViewById<TextInputLayout>(R.id.titleInput)
        val descriptionInputLayout = findViewById<TextInputLayout>(R.id.descriptionInput)

        var selectedColor = 0 // Default color

        todayBtn.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                selectedColor = Color.parseColor(todayColor) // Example color, you can set this to your desired color
            }
        }

        tmrBtn.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                selectedColor = Color.parseColor(tmrColor) // Example color, you can set this to your desired color
            }
        }

        laterBtn.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                selectedColor = Color.parseColor(ltrColor) // Example color, you can set this to your desired color
            }
        }

        addButton.setOnClickListener {
            val title = titleInputLayout.editText?.text.toString()
            val description = descriptionInputLayout.editText?.text.toString()

            // Save the task details, deadline, and color to a cache file
            saveTaskToCacheFile(title, description, selectedColor)

            // Pass the event details and selected color to EventList activity
            val intent = Intent(this, EventList::class.java).apply {
                putExtra("title", title)
                putExtra("description", description)
                putExtra("color", selectedColor)
            }
            startActivity(intent)
        }

    }

    private fun saveTaskToCacheFile(title: String, description: String, color: Int) {
        val cacheDir = cacheDir // Directory to store cache files
        val cacheFile = File.createTempFile("tasks", null, cacheDir)
        val taskData = "$title,$description,$color\n"
        cacheFile.appendText(taskData)
    }
 */