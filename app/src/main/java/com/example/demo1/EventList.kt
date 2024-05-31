package com.example.demo1

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class EventList : AppCompatActivity() {
    private lateinit var eventRecyclerView: RecyclerView
    private lateinit var events: MutableList<Event>
    private lateinit var filteredEvents: MutableList<Event> // Store filtered events

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_list)
        supportActionBar?.hide()

        val taskBtn: Button = findViewById(R.id.taskBtn)
        val resetBtn: Button = findViewById(R.id.reset)
        val todayFilterBtn: Button = findViewById(R.id.todayFilter)
        val tmrFilterBtn: Button = findViewById(R.id.tmrFilter)
        val ltrFilterBtn: Button = findViewById(R.id.ltrFilter)
        val viewAllBtn: Button = findViewById(R.id.viewAllBtn)

        val homeBtn: ImageView = findViewById(R.id.homeBtn)

        homeBtn.setOnClickListener {
            // Open other view; create intent
            val taskIntent = Intent(this, Home::class.java)
            // Start activity
            startActivity(taskIntent)
        }

        taskBtn.setOnClickListener {
            // Open other view; create intent
            val taskIntent = Intent(this, EventForm::class.java)
            // Start activity
            startActivity(taskIntent)
        }

        resetBtn.setOnClickListener {
            // Open other view; create intent
            clearTasks()
        }

        viewAllBtn.setOnClickListener {
            // Open other view; create intent
            showAllTasks()
        }

        todayFilterBtn.setOnClickListener { filterEventsByDeadline("#FE7488") } // Today color
        tmrFilterBtn.setOnClickListener { filterEventsByDeadline("#FFB074") } // Tomorrow color
        ltrFilterBtn.setOnClickListener { filterEventsByDeadline("#EDDA5D") } // Later color

        eventRecyclerView = findViewById(R.id.eventRecyclerView)
        eventRecyclerView.layoutManager = LinearLayoutManager(this)

        // Get events from intent or cache file
        events = getEventsFromIntent().toMutableList()
        events.addAll(getEventsFromCacheFile())

        // Initially show all events
        filteredEvents = ArrayList(events)

        // Set up adapter with filtered events
        val adapter = EventAdapter(filteredEvents) { event ->
            removeEvent(event)
        }
        eventRecyclerView.adapter = adapter
    }

    private fun filterEventsByDeadline(color: String) {
        // Filter events by deadline color
        filteredEvents.clear()
        filteredEvents.addAll(events.filter { it.color == Color.parseColor(color) })
        eventRecyclerView.adapter?.notifyDataSetChanged()
    }

    private fun getEventsFromIntent(): List<Event> {
        val intentEvents = mutableListOf<Event>()

        // Retrieve events from intent
        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val color = intent.getIntExtra("color", Color.WHITE) // Default color if not provided

        if (title != null && description != null) {
            // Check if the event already exists in the list
            if (!events.any { it.title == title && it.description == description && it.color == color }) {
                intentEvents.add(Event(title, description, color))
            }
        }

        return intentEvents
    }

    private fun getEventsFromCacheFile(): List<Event> {
        val cacheDir = cacheDir // Directory to store cache files
        val cacheFile = File(cacheDir, "events")
        val cacheEvents = mutableListOf<Event>()
        if (cacheFile.exists()) {
            cacheFile.forEachLine {
                val (title, description, colorStr) = it.split(",")
                val color = colorStr.toInt()
                cacheEvents.add(Event(title, description, color))
            }
        }
        return cacheEvents
    }

    private fun clearTasks() {
        // Clear the list of events
        events.clear()
        // Notify the adapter that data set has changed
        eventRecyclerView.adapter?.notifyDataSetChanged()

        // Optionally, you can also delete the cache file or any other storage mechanism you're using
        deleteCacheFile()
    }

    private fun deleteCacheFile() {
        val cacheDir = cacheDir // Directory where cache files are stored
        val cacheFile = File(cacheDir, "events")
        // Delete the cache file if it exists
        if (cacheFile.exists()) {
            cacheFile.delete()
        }
    }

    private fun showAllTasks() {
        filteredEvents.clear()
        filteredEvents.addAll(events)
        eventRecyclerView.adapter?.notifyDataSetChanged()
    }

    private fun removeEvent(event: Event) {
        events.remove(event)
        filteredEvents.remove(event)
        eventRecyclerView.adapter?.notifyDataSetChanged()
        saveEventsToCacheFile() // Update the cache file after removing the event
    }

    private fun saveEventsToCacheFile() {
        val cacheDir = cacheDir // Directory to store cache files
        val cacheFile = File(cacheDir, "events")
        cacheFile.printWriter().use { writer ->
            events.forEach {
                writer.println("${it.title},${it.description},${it.color}")
            }
        }
    }
}

/*class EventList : AppCompatActivity() {
    private lateinit var eventRecyclerView: RecyclerView
    private lateinit var events: MutableList<Event>
    private lateinit var filteredEvents: MutableList<Event> // Store filtered events

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_list)
        supportActionBar?.hide()

        val taskBtn: Button = findViewById(R.id.taskBtn)
        val resetBtn: Button = findViewById(R.id.reset)
        val todayFilterBtn: Button = findViewById(R.id.todayFilter)
        val tmrFilterBtn: Button = findViewById(R.id.tmrFilter)
        val ltrFilterBtn: Button = findViewById(R.id.ltrFilter)
        val viewAllBtn: Button = findViewById(R.id.viewAllBtn)

        val homeBtn: ImageView = findViewById(R.id.homeBtn)

        homeBtn.setOnClickListener {
            // Open other view; create intent
            val taskIntent = Intent(this, Home::class.java)
            // Start activity
            startActivity(taskIntent)
        }

        taskBtn.setOnClickListener {
            // Open other view; create intent
            val taskIntent = Intent(this, EventForm::class.java)
            // Start activity
            startActivity(taskIntent)
        }

        resetBtn.setOnClickListener {
            // Open other view; create intent
            clearTasks()
        }

        viewAllBtn.setOnClickListener {
            // Open other view; create intent
            showAllTasks()
        }

        todayFilterBtn.setOnClickListener { filterEventsByDeadline("#FE7488") } // Today color
        tmrFilterBtn.setOnClickListener { filterEventsByDeadline("#FFB074") } // Tomorrow color
        ltrFilterBtn.setOnClickListener { filterEventsByDeadline("#EDDA5D") } // Later color

        eventRecyclerView = findViewById(R.id.eventRecyclerView)
        eventRecyclerView.layoutManager = LinearLayoutManager(this)

        // Get events from intent or cache file
        events = getEventsFromIntent().toMutableList()
        events.addAll(getEventsFromCacheFile())

        // Initially show all events
        filteredEvents = ArrayList(events)

        // Set up adapter with filtered events
        // Set up adapter with filtered events
        val adapter = EventAdapter(filteredEvents) { event ->
            removeEvent(event)
        }
        eventRecyclerView.adapter = adapter
    }

    private fun filterEventsByDeadline(color: String) {
        // Filter events by deadline color
        filteredEvents.clear()
        filteredEvents.addAll(events.filter { it.color == Color.parseColor(color) })
        eventRecyclerView.adapter?.notifyDataSetChanged()
    }


    private fun getEventsFromIntent(): List<Event> {
        val intentEvents = mutableListOf<Event>()

        // Retrieve events from intent
        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val color = intent.getIntExtra("color", Color.WHITE) // Default color if not provided

        if (title != null && description != null) {
            // Check if the event already exists in the list
            if (!events.any { it.title == title && it.description == description && it.color == color }) {
                intentEvents.add(Event(title, description, color))
            }
        }

        return intentEvents
    }


    private fun getEventsFromCacheFile(): List<Event> {
        val cacheDir = cacheDir // Directory to store cache files
        val cacheFile = File(cacheDir, "events")
        val cacheEvents = mutableListOf<Event>()
        if (cacheFile.exists()) {
            cacheFile.forEachLine {
                val (title, description, colorStr) = it.split(",")
                val color = colorStr.toInt()
                cacheEvents.add(Event(title, description, color))
            }
        }
        return cacheEvents
    }


    private fun clearTasks() {
        // Clear the list of events
        events.clear()
        // Notify the adapter that data set has changed
        eventRecyclerView.adapter?.notifyDataSetChanged()


        // Optionally, you can also delete the cache file or any other storage mechanism you're using
        deleteCacheFile()

    }


    private fun deleteCacheFile() {
        val cacheDir = cacheDir // Directory where cache files are stored
        val cacheFile = File(cacheDir, "events")
        // Delete the cache file if it exists
        if (cacheFile.exists()) {
            cacheFile.delete()
        }
    }

    private fun showAllTasks() {
        filteredEvents.clear()
        filteredEvents.addAll(events)
        eventRecyclerView.adapter?.notifyDataSetChanged()
    }

    private fun removeEvent(event: Event) {
        events.remove(event)
        filteredEvents.remove(event)
        eventRecyclerView.adapter?.notifyDataSetChanged()
    }
}*/

