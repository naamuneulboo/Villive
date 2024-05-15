package com.example.villive.Community_Write

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.villive.R

class Post_Complain : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.post_complain)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 스피너 초기화
        val spinner = findViewById<Spinner>(R.id.sp_item)
        val complainArray = resources.getStringArray(R.array.complain_array)
        val complainAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, complainArray)
        complainAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = complainAdapter

        // 전달된 selectedItem 확인하고 선택된 항목으로 설정
        val selectedItem = intent.getStringExtra("selectedItem")
        val index = complainArray.indexOf(selectedItem)
        spinner.setSelection(index)


        val btnAddPost = findViewById<View>(R.id.btn_add_post)
        btnAddPost.setOnClickListener {
            val selectedItem = spinner.selectedItem.toString()
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

                val message = buildString {
                    append("선택된 항목: $selectedItem\n")
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

