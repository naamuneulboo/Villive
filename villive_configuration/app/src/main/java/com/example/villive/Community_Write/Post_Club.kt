package com.example.villive.Community_Write

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

        val btnAddNoti = findViewById<View>(R.id.btn_add_noti)
        btnAddNoti.setOnClickListener {
            val isAnonymous = checkBoxAnonymous.isChecked
            val editTextContent = findViewById<EditText>(R.id.et_notice_input).text.toString()

            val message = buildString {
                append("익명 여부: ${if (isAnonymous) "익명" else "프로필"}\n")
                append("내용: $editTextContent")
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