package com.example.villive.Community

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.villive.R

class Post_Detail_View : AppCompatActivity() {
    private lateinit var titleTextView: TextView
    private lateinit var contentsTextView: TextView
    private lateinit var writerTextView: TextView
    private lateinit var createDateTextView: TextView
    private lateinit var contentsId: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_detail_view)

        titleTextView = findViewById(R.id.post_title)
        contentsTextView = findViewById(R.id.post_contents)
        writerTextView = findViewById(R.id.post_writer)
        createDateTextView = findViewById(R.id.post_create_date)
        contentsId = findViewById(R.id.post_id)

        // Intent로부터 데이터 가져오기
        val postId = intent.getLongExtra("POST_ID", -1)
        val postTitle = intent.getStringExtra("POST_TITLE")
        val postContents = intent.getStringExtra("POST_CONTENTS")
        val postWriter = intent.getStringExtra("POST_WRITER")
        val postCreateDate = intent.getStringExtra("POST_CREATE_DATE")

        // 가져온 데이터 설정
        titleTextView.text = postTitle
        contentsTextView.text = postContents
        writerTextView.text = postWriter
        createDateTextView.text = postCreateDate
        contentsId.text = postId.toString()
    }
}