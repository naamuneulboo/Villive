package com.example.villive.Community

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.villive.R
import com.example.villive.model.ComplainResponseDto

class ComplainAdapter(private val complainList: List<ComplainResponseDto>) :
    RecyclerView.Adapter<ComplainAdapter.ComplainViewHolder>() {

    class ComplainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val contents: TextView = itemView.findViewById(R.id.contents)
        val writer: TextView = itemView.findViewById(R.id.writer)
        val createdDate: TextView = itemView.findViewById(R.id.createdDate)
        val status: TextView = itemView.findViewById(R.id.status)
        val type: TextView = itemView.findViewById(R.id.type)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComplainViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_complain_response, parent, false)
        return ComplainViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ComplainViewHolder, position: Int) {
        val currentItem = complainList[position]
        holder.title.text = currentItem.title
        holder.contents.text = currentItem.contents
        holder.writer.text = currentItem.writer
        holder.createdDate.text = currentItem.createdDate
        holder.status.text = currentItem.status?.name
        holder.type.text = currentItem.type?.name

        // 상태에 따라 텍스트 색상 변경
        holder.status.apply {
            text = currentItem.status?.name
            when (currentItem.status) {
                ComplainResponseDto.Status.접수 -> setTextColor(Color.BLACK)
                ComplainResponseDto.Status.처리중 -> setTextColor(Color.RED)
                ComplainResponseDto.Status.완료 -> setTextColor(Color.BLUE)
                else -> setTextColor(Color.BLACK)
            }
        }
    }

    override fun getItemCount() = complainList.size
}
