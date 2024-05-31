package com.example.demo1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EventAdapter(
    private val events: MutableList<Event>,
    private val onComplete: (Event) -> Unit
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event, parent, false)
        return EventViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.titleTextView.text = event.title
        holder.descriptionTextView.text = event.description
        holder.itemLayout.setBackgroundColor(event.color)

        holder.completeBtn.setOnClickListener {
            onComplete(event)
        }
    }

    override fun getItemCount(): Int {
        return events.size
    }

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val itemLayout: LinearLayout = itemView.findViewById(R.id.itemLayout)
        val completeBtn: Button = itemView.findViewById(R.id.completeBtn)
    }
}


/*class EventAdapter(
    private val events: MutableList<Event>,
    private val onComplete: (Event) -> Unit
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event, parent, false)
        return EventViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.titleTextView.text = event.title
        holder.descriptionTextView.text = event.description
        holder.itemLayout.setBackgroundColor(event.color)

        holder.completeBtn.setOnClickListener {
            onComplete(event)
        }
    }

    override fun getItemCount(): Int {
        return events.size
    }

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val itemLayout: LinearLayout = itemView.findViewById(R.id.itemLayout)
        val completeBtn: Button = itemView.findViewById(R.id.completeBtn)
    }
}*/
