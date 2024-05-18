package com.example.villive.ConfigurationPage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.villive.R
import com.example.villive.User_SignPage.user_SignUp


class UnregisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.unregister)
        initializeComponents()

        val tb = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(tb) // 액티비티의 Appbar로 지정
        val actionBar = supportActionBar // 앱바 제어를 위한 툴바 액세스
        actionBar!!.setDisplayHomeAsUpEnabled(true) // 앱바에 뒤로가기 버튼 만들기
        actionBar.setDisplayShowTitleEnabled(false) // 기존 title 숨김
    }

    //앱바(App Bar)에 표시된 액션 또는 오버플로우 메뉴가 선택되면 액티비티의 onOptionsItemSelected() 메서드 호출
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
        val editPw = findViewById<EditText>(R.id.editTextPassword)
        val btnUnregister = findViewById<Button>(R.id.buttonUnregister)

        btnUnregister.setOnClickListener {
            val pw = editPw.text.toString()

            if (pw.trim().isEmpty()) {
                // 공백이 있을 경우
                Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            showConfirmationDialog()
        }
    }

    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("회원탈퇴를 하시겠습니까?\n회원탈퇴 시 복구할 수 없습니다.")
            .setPositiveButton("예") { dialog, which ->
                // "예"를 선택한 경우 회원가입 화면으로 이동
                // db에서 유저 삭제
                val intent = Intent(this, user_SignUp::class.java)
                startActivity(intent)
                // 회원탈퇴화면 finish
                finish()
            }
            .show()
    }
}
