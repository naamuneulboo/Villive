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


        // 이건 그냥 게시글 등록 버튼 눌렀을 때 내용 확인용으로 써둔 코드
        val checkBoxAnonymous = findViewById<CheckBox>(R.id.checkBoxAnonymous)
        checkBoxAnonymous.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                checkBoxAnonymous.text = getString(R.string.anonymous)
            } else {
                checkBoxAnonymous.text = getString(R.string.profile)
            }
        }

        val btnAddPost = findViewById<View>(R.id.btn_add_post)
        btnAddPost.setOnClickListener {
            val isAnonymous = checkBoxAnonymous.isChecked
            val PostTitle = findViewById<EditText>(R.id.et_post_title).text.toString()
            val PostWrite = findViewById<EditText>(R.id.et_post_write).text.toString()

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
                    append("익명 여부: ${if (isAnonymous) "익명" else "프로필"}\n")
                    append("제목: $PostTitle\n")
                    append("내용: $PostWrite")
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