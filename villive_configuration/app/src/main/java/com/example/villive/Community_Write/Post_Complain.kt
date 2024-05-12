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

        // 체크 박스 리스너 설정
        val checkBoxAnonymous = findViewById<CheckBox>(R.id.checkBoxAnonymous)
        checkBoxAnonymous.isChecked = true

        checkBoxAnonymous.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                checkBoxAnonymous.text = getString(R.string.anonymous)
            } else {
                checkBoxAnonymous.text = getString(R.string.profile)
            }
        }

        // 게시글 등록 버튼 클릭 리스너 설정
        val btnAddNoti = findViewById<View>(R.id.btn_add_noti)
        btnAddNoti.setOnClickListener {
            val selectedItem = spinner.selectedItem.toString()
            val isAnonymous = checkBoxAnonymous.isChecked
            val editTextContent = findViewById<EditText>(R.id.et_notice_input).text.toString()

            val message = buildString {
                append("선택된 항목: $selectedItem\n")
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

