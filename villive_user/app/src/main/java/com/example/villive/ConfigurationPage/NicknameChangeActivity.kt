package com.example.villive.ConfigurationPage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.villive.R
import com.example.villive.SettingFragment

class NicknameChangeActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.nickname_change)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tb = findViewById<View>(R.id.main) as Toolbar
        setSupportActionBar(tb) // 액티비티의 앱바로 지정
        val actionBar = supportActionBar // 앱바 제어를 위한 툴바 액세스
        actionBar!!.setDisplayHomeAsUpEnabled(true) // 앱바에 뒤로가기 버튼 만들기
        actionBar.setDisplayShowTitleEnabled(false) // 기존 title 숨김

        val inputEditNickname = findViewById<EditText>(R.id.editTextNickname)
        val buttonCheck = findViewById<Button>(R.id.buttonCheck)

        buttonCheck.setOnClickListener {
            val nickname = inputEditNickname.text.toString()
            if (nickname.trim().isEmpty()) {
                // 공백이 있을 경우
                Toast.makeText(this, "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                val intent = Intent(this, SettingFragment::class.java)
                startActivity(intent)
            }
        }
    }

    //앱바(App Bar)에 표시된 액션 또는 오버플로우 메뉴가 선택되면 액티비티의 onOptionsItemSelected() 메서드가 호출
    @SuppressLint("NonConstantResourceId")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}