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
import com.example.villive.Community_Write.Post_Group
import com.example.villive.Post_model_adapter.PostAdapter
import com.example.villive.Post_model_adapter.Posts
import com.example.villive.R

class Community_Group : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        val postlist= arrayListOf(
            Posts("test","test","가연","10:01"),
            Posts("test","test","가연","10:03"),
            Posts("test","test","가연","10:40"),
            Posts("404호 백현서??","욕하지마세요","가연","09:03"),
            Posts("test","test","가연","11:07"),
            Posts("test","test","가연","11:53"),


            )

        val rv_post = findViewById<RecyclerView>(R.id.rv_posts_group)

        rv_post.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        rv_post.setHasFixedSize(true)

        rv_post.adapter= PostAdapter(postlist)


        setContentView(R.layout.community_group)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val write_post = findViewById<Button>(R.id.btn_write_post)

        write_post.setOnClickListener {
            val intent = Intent(this, Post_Group::class.java)
            startActivity(intent)
        }
    }
}