package com.example.villive.Building_Issue

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.villive.R
import com.example.villive.model.PostsResponseDto

class IssueAdapter(private val posts: List<PostsResponseDto>) :
    RecyclerView.Adapter<IssueAdapter.IssueViewHolder>() {

    private var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(post: PostsResponseDto)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_issue, parent, false)
        return IssueViewHolder(view)
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        val post = posts[position]
        holder.tvTitle.text = post.title
        holder.tvContents.text = post.contents
        holder.tvWriter.text = post.writer
        holder.tvCreateDate.text = post.createDate
        holder.tvLikes.text = "${post.postsLikeCnt ?: 0} 공감"

        holder.itemView.setOnClickListener {
            listener?.onItemClick(post)
        }
    }

    override fun getItemCount() = posts.size

    class IssueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvContents: TextView = itemView.findViewById(R.id.tvContents)
        val tvWriter: TextView = itemView.findViewById(R.id.tvWriter)
        val tvCreateDate: TextView = itemView.findViewById(R.id.tvCreateDate)
        val tvLikes: TextView = itemView.findViewById(R.id.tvLikes)
    }
}