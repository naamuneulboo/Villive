package com.example.villive.Post_model_adapter

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog.show
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.villive.R
import com.example.villive.Community.Community_Content


class PostAdapter (val PostList: ArrayList<Posts>) : RecyclerView.Adapter<PostAdapter.CustomViewHolder>(){


   /* override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder { // 연결 될 화면이 무엇인지 ! (LayoutInflater로 화면 붙이기)
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_post_column, parent, false)
        return CustomViewHolder(view).apply { // 뷰를 생성해서 CustomViewHolder로 전달 (즉, CustomViewHolder의 itemView = view)
            itemView.setOnClickListener {  // 눌렀을때 토스트로 내용 뽁 띄워주는거
                val curPos: Int = adapterPosition
                val post: Posts =PostList.get(curPos)
                Toast.makeText(parent.context,"제목 : ${post.title} \n내용 : ${post.write} \n닉네임 : ${post.nickname} \n등록 시간 : ${post.write_time}",Toast.LENGTH_LONG).show()

            }
        }
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_post_column, parent, false)
        return CustomViewHolder(view).apply {
            itemView.setOnClickListener {
                val curPos: Int = adapterPosition
                val post: Posts = PostList[curPos]

                // Intent로 새로운 Activity로 이동
                val intent = Intent(parent.context, Community_Content::class.java).apply {
                    putExtra("title", post.title)
                    putExtra("write", post.write)
                    putExtra("nickname", post.nickname)
                    putExtra("write_time", post.write_time)
                }
                parent.context.startActivity(intent)
            }


        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) { // 실제로 연결 되면서 데이터를 매치해주는 메소드
        holder.title.text=PostList.get(position).title
        holder.write.text=PostList.get(position).write
        holder.nickname.text=PostList.get(position).nickname
        holder.write_time.text=PostList.get(position).write_time
    }

    override fun getItemCount(): Int { // 리스트 수 (총 길이)
        return PostList.size
    }


    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) { // 뷰 연결할 때 사용

        val title = itemView.findViewById<TextView>(R.id.tv_titlte)
        val write = itemView.findViewById<TextView>(R.id.tv_write)
        val nickname = itemView.findViewById<TextView>(R.id.tv_nickname)
        val write_time = itemView.findViewById<TextView>(R.id.tv_write_time)

    }

}