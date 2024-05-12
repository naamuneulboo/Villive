package com.example.villive.ConfigurationPage

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.villive.R

class ProfileSetupActivity : AppCompatActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            R.layout.profile_setup
        )
        initializeComponents()
        val tb = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(tb) // 액티비티의 앱바로 지정
        val actionBar = supportActionBar // 앱바 제어를 위한 툴바 액세스
        actionBar!!.setDisplayHomeAsUpEnabled(true) // 앱바에 뒤로가기 버튼 만들기
        actionBar.setDisplayShowTitleEnabled(false) // 기존 title 숨김
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

    fun initializeComponents() {
        val ImageProfile = findViewById<ImageView>(R.id.imProfile)
        val TextArticleAlarm = findViewById<TextView>(R.id.textNickname)
        val inputEditNickname = findViewById<EditText>(R.id.editTextNickname)
        val buttonReCheck = findViewById<Button>(R.id.buttonRepetedCheck)
        val buttonCheck = findViewById<Button>(R.id.buttonCheck)

        buttonReCheck.setOnClickListener {
            val ninkname = inputEditNickname.text.toString()

            // 닉네임 디비랑 중복체크하는 코드 들어가야함.
        }
    }
}
