package com.example.villive.Management


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.villive.R
import com.example.villive.model.CarResponseDto

interface CarClickListener {
    fun onDeleteClick(car: CarResponseDto)
}
class CarAdapter(private var cars: List<CarResponseDto>, private val listener: CarClickListener) : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    inner class CarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val carNumTextView: TextView = view.findViewById(R.id.carNum_tv)
        val carStatusTextView: TextView = view.findViewById(R.id.carStatus_tv)
        private val overflowMenu: ImageView = view.findViewById(R.id.car_menu_view)

        init {
            overflowMenu.setOnClickListener {
                val car = cars[adapterPosition]
                showPopupMenu(overflowMenu, car)
            }
        }

        private fun showPopupMenu(overflowMenu: ImageView, car: CarResponseDto) {
            val popupMenu = PopupMenu(overflowMenu.context, overflowMenu)
            popupMenu.inflate(R.menu.car_menu_option)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_delete -> {
                        listener.onDeleteClick(car) // 삭제 액션 처리
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }
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
