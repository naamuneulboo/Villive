package com.example.villive.Community

import android.annotation.SuppressLint
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
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.community_share)


        val postlist= arrayListOf(
            Posts("물","물 나눔","가연","09:03"),
            Posts("책상","책상 나눔","가연","10:01"),
            Posts("밥","밥 나눔","가연","10:03"),
            Posts("옷","옷 나눔","가연","10:40"),
            Posts("치약","치약 나눔","가연","11:07"),
            Posts("세제","세제 나눔","가연","11:53"),
            Posts("옷걸이","옷걸이 나눔","가연","12:04"),
            Posts("휴지","휴지 나눔","가연","13:47"),
            Posts("식탁","식탁 나눔","가연","13:55"),
            Posts("이불","이불 나눔","가연","14:08"),
            Posts("노트북","노트북 나눔","가연","14:53"),
            Posts("멀티탭","멀티탭 나눔","가연","16:03"),
            Posts("텀블러","텀블러 나눔","가연","16:47"),
            Posts("종이컵","종이컵 나눔","가연","17:03"),

            )

        val rv_post = findViewById<RecyclerView>(R.id.rv_posts_share)

        rv_post.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        rv_post.setHasFixedSize(true)

        rv_post.adapter=PostAdapter(postlist)

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