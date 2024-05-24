package com.example.villive.Community_Write

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.villive.R

class Post_Club : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.post_club)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnAddPost = findViewById<View>(R.id.btn_add_post)
        btnAddPost.setOnClickListener {
            val PostTitle = findViewById<EditText>(R.id.et_post_title).text.toString()
            val PostWrite = findViewById<EditText>(R.id.et_post_write).text.toString()
            val club="동호회"
            // 게시글 쓰기에 카테고리 선택하는거 넣으면 게시판 나눈 의미가 없는거같아서
            // 게시글 등록 버튼 누르면 "동호회" 텍스트가 category에 지정되게 하는것도 괜찮을 듯 ..!

            if (PostTitle.isEmpty() || PostWrite.isEmpty()) {
                // 제목 또는 내용이 공백인 경우 다이얼로그 표시
                val message = getString(R.string.add_post_error_message)
                AlertDialog.Builder(this)
                    .setMessage(message)
                    .setPositiveButton("확인") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            } else {
                // 제목과 내용이 모두 채워져 있는 경우 다이얼로그 표시
                val message = buildString {
                    append("제목: $PostTitle\n")
                    append("내용: $PostWrite\n")
                    append("게시판 유형: $club")
                }
                AlertDialog.Builder(this)
                    .setMessage(message)
                    .setPositiveButton("확인") { dialog, _ ->
                        dialog.dismiss()
                        finish() // 이전 화면으로
                    }
                    .show()
            }
        }
    }
}