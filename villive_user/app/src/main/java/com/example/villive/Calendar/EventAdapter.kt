package com.example.villive.Calendar


import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.villive.Calendar.C_event
import com.example.villive.R

class EventAdapter(private var eventList: List<C_event>) :
    RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val eventNameTextView: TextView = itemView.findViewById(R.id.eventNameTextView)
        val eventDateTextView: TextView = itemView.findViewById(R.id.eventdayTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = eventList[position]
        holder.eventNameTextView.text = event.eventName
        holder.eventDateTextView.text = event.date
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    fun updateEvents(newEvents: List<C_event>) {
        eventList = newEvents
        notifyDataSetChanged()
    }
}