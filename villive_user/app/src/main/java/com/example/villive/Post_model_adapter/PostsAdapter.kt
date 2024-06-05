package com.example.villive.Post_model_adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.villive.R
import com.example.villive.model.PostsResponseDto

class PostsAdapter(private val postsList: List<PostsResponseDto>) :
    RecyclerView.Adapter<PostsAdapter.PostsViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(post: PostsResponseDto)
    }

    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post_response, parent, false)
        return PostsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        val currentItem = postsList[position]
        holder.title.text = currentItem.title
        holder.contents.text = currentItem.contents
        holder.writer.text = currentItem.writer
        holder.createDate.text = currentItem.createDate
        if (currentItem.postsLikeCnt == 0) {
            holder.likes.visibility = View.GONE
        } else {
            holder.likes.visibility = View.VISIBLE
            holder.likes.text = "${currentItem.postsLikeCnt}"
        }

        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(currentItem)
        }
    }

    override fun getItemCount() = postsList.size

    class PostsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val contents: TextView = itemView.findViewById(R.id.contents)
        val writer: TextView = itemView.findViewById(R.id.writer)
        val createDate: TextView = itemView.findViewById(R.id.createDate)
        val likes: TextView = itemView.findViewById(R.id.likes)
    }
}
