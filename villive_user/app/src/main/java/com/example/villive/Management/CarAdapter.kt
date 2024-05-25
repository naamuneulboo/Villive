package com.example.villive.Management

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.villive.R
import com.example.villive.model.CarResponseDto

class CarAdapter(private var cars: List<CarResponseDto>) : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    inner class CarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val carNumTextView: TextView = view.findViewById(R.id.carNum_tv)
        val carStatusTextView: TextView = view.findViewById(R.id.carStatus_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_car, parent, false)
        return CarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = cars[position]
        holder.carNumTextView.text = car.carNum
        holder.carStatusTextView.text = car.status
    }

    override fun getItemCount(): Int {
        return cars.size
    }

    fun updateCars(newCars: List<CarResponseDto>) {
        cars = newCars
        notifyDataSetChanged()
    }
}
