package com.example.villive.Post_model_adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.villive.R
import org.w3c.dom.Text

class ComplainAdapter(private val complainList: ArrayList<Complain>,  private val context: Context) : RecyclerView.Adapter<ComplainAdapter.ComplainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComplainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_post_complain_column, parent, false)
        return ComplainViewHolder(view)
    }

    override fun onBindViewHolder(holder: ComplainViewHolder, position: Int) {
        val currentComplain = complainList[position]
        holder.title.text = currentComplain.title
        holder.contents.text = currentComplain.contents
        holder.writer.text = currentComplain.writer
        holder.createdTime.text = currentComplain.createdTime
        holder.type.text = currentComplain.type
        holder.status.text = currentComplain.status

        // Set text color based on status using resources
        when (currentComplain.status) {
            "접수" -> holder.status.setTextColor(ContextCompat.getColor(context, R.color.status_gray))
            "처리 중" -> holder.status.setTextColor(ContextCompat.getColor(context, R.color.status_blue))
            "처리 완료" -> holder.status.setTextColor(ContextCompat.getColor(context, R.color.status_red))
        }
    }

    override fun getItemCount(): Int {
        return complainList.size
    }

    class ComplainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tv_titlte)
        val contents: TextView = itemView.findViewById(R.id.tv_contents)
        val writer: TextView = itemView.findViewById(R.id.tv_writer)
        val createdTime: TextView = itemView.findViewById(R.id.tv_created_date)
        val type: TextView = itemView.findViewById(R.id.tv_type)
        val status: TextView = itemView.findViewById(R.id.tv_status)
    }
}
