package com.example.villive.Post_model_adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.villive.R

class ComplainAdapter(private val complainList: ArrayList<Complain>) : RecyclerView.Adapter<ComplainAdapter.ComplainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComplainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_post_complain_column, parent, false)
        return ComplainViewHolder(view)
    }

    override fun onBindViewHolder(holder: ComplainViewHolder, position: Int) {
        val currentComplain = complainList[position]
        holder.title.text = currentComplain.title
        holder.write.text = currentComplain.write
        holder.nickname.text = currentComplain.nickname
        holder.writeTime.text = currentComplain.write_time
        holder.complainKind.text = currentComplain.complain_kind
    }

    override fun getItemCount(): Int {
        return complainList.size
    }

    class ComplainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tv_titlte)
        val write: TextView = itemView.findViewById(R.id.tv_write)
        val nickname: TextView = itemView.findViewById(R.id.tv_nickname)
        val writeTime: TextView = itemView.findViewById(R.id.tv_write_time)
        val complainKind: TextView = itemView.findViewById(R.id.tv_complain_kind)
    }
}