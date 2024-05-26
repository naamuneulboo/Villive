package com.example.villive.Post_model_adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.villive.Community_Write.Post_Detail_View
import com.example.villive.model.CommentResponseDto
import com.example.villive.R

class CommentAdapter(private val comments: List<CommentResponseDto>) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    // 인터페이스 정의
    interface OnItemDeleteClickListener {
        fun onDeleteClick(commentId: Long)
    }

    // 클릭 리스너 변수 선언
    private var onDeleteClickListener: OnItemDeleteClickListener? = null

    fun setOnDeleteClickListener(listener: Post_Detail_View) {
        this.onDeleteClickListener = listener
    }

    inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val writerTextView: TextView = itemView.findViewById(R.id.tv_comment_writer)
        private val contentTextView: TextView = itemView.findViewById(R.id.tv_comment_content)
        private val timeTextView: TextView = itemView.findViewById(R.id.tv_comment_time)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.btn_comment_delete)

        fun bind(comment: CommentResponseDto) {
            writerTextView.text = comment.writer
            contentTextView.text = comment.content
            timeTextView.text = comment.createdDate

            // 삭제 버튼 클릭 시
            deleteButton.setOnClickListener {
                onDeleteClickListener?.onDeleteClick(comment.id) // 클릭한 댓글의 ID를 전달
            }
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
