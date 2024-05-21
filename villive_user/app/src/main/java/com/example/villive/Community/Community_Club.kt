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
import com.example.villive.Community_Write.Post_Club
import com.example.villive.Post_model_adapter.PostAdapter
import com.example.villive.Post_model_adapter.Posts
import com.example.villive.R

class Community_Club : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.community_club)



        val postlist= arrayListOf(
            Posts("등산","같이해요","가연","09:03"),
            Posts("배그","같이해요","가연","10:01"),
            Posts("배드민턴","같이해요","가연","10:03"),
            Posts("공부","같이해요","가연","10:40"),
            Posts("토익","같이해요","가연","11:07"),
            Posts("코딩","같이해요","가연","11:53"),
            Posts("공모전","같이해요","가연","12:04"),
            Posts("정원꾸미기","같이해요","가연","13:47"),
            Posts("클라이밍","같이해요","가연","13:55"),
            )

        val rv_post = findViewById<RecyclerView>(R.id.rv_posts_club)

        rv_post.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        rv_post.setHasFixedSize(true)

        rv_post.adapter= PostAdapter(postlist)



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val write_post = findViewById<Button>(R.id.btn_write_post)

        write_post.setOnClickListener {
            val intent = Intent(this, Post_Club::class.java)
            startActivity(intent)
        }
    }
}