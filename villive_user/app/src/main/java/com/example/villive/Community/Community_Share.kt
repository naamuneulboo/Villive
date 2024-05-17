package com.example.villive.Community

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.villive.Community_Write.Post_Share
import com.example.villive.Post_model_adapter.PostAdapter
import com.example.villive.Post_model_adapter.Posts
import com.example.villive.R

class Community_Share : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.community_share)


        val postlist=arrayListOf(
            Posts("행거 나눔해요","튼튼하고 깨끗해요","404호","20:33"),
            Posts("옷걸이 나눔해요","10개 가져가실분","익명","21:36"),
            Posts("생수 나눔해요","2리터 3개 가져가실분","103호","21:39"),
            Posts("과자 나눔해요","호수 알려주시면 집앞에 둘게요","익명","22:46"),
            Posts("세제 나눔해요","4리터 가져가실분","202호","22:49"),
            Posts("의자 나눔해요","문앞에 둘테니 가져가세요","101호","23:00"),
            Posts("섬유유연제 나눔해요","스너글 코튼향입니다","503호","23:36"),
            Posts("책상 나눔해요","직접 가져다 드려요","익명","23:45"),
            Posts("식탁 나눔해요","기스난거 빼고는 괜찮습니다.","익명","23:57")

        )

        val rv_Posts= findViewById<RecyclerView>(R.id.rv_posts)

        rv_Posts.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)
        rv_Posts.setHasFixedSize(true)
        rv_Posts.adapter= PostAdapter(postlist)




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val write_post = findViewById<Button>(R.id.btn_write_post)

        write_post.setOnClickListener {
            val intent = Intent(this, Post_Share::class.java)
            startActivity(intent)
        }
    }
}