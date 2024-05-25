package com.example.villive.Post_model_adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.villive.model.CommentResponseDto
import com.example.villive.R

class CommentAdapter(private val comments: List<CommentResponseDto>) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val writerTextView: TextView = itemView.findViewById(R.id.tv_comment_writer)
        private val contentTextView: TextView = itemView.findViewById(R.id.tv_comment_content)
        private val timeTextView: TextView = itemView.findViewById(R.id.tv_comment_time)

        fun bind(comment: CommentResponseDto) {
            writerTextView.text = comment.writer
            contentTextView.text = comment.content
            timeTextView.text = comment.createdDate
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(comments[position])
    }

    override fun getItemCount(): Int {
        return comments.size
    }
}