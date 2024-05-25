package com.example.villive.ConfigurationPage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.villive.R
import com.example.villive.User_SignPage.user_LogIn

class PasswordChangeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.password_change)
        initializeComponents()
    }

    // 비밀번호 변경 페이지
    fun initializeComponents() {
        val inputEditPassword = findViewById<EditText>(R.id.editTextPassword)
        val inputEditNewPassword = findViewById<EditText>(R.id.editTextNewPassword)
        val inputEditCheckPassword = findViewById<EditText>(R.id.editTextCheckPassword)
        val buttonChange = findViewById<Button>(R.id.buttonChange)

        buttonChange.setOnClickListener {

            val nowPw = inputEditPassword.text.toString()
            val newPw = inputEditNewPassword.text.toString()
            val pwCheck = inputEditCheckPassword.text.toString()

            if (nowPw.trim().isEmpty() || newPw.trim().isEmpty() || pwCheck.trim().isEmpty()) {
                // 공백이 있을 경우
                Toast.makeText(this, "모든 필드는 공백일 수 없습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 비밀번호 유효성 검사: 영어와 특수문자 포함 8~12자리
            val pwPattern = Regex("(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9])(?=\\S+$).{8,12}")
            if (!pwPattern.matches(newPw)) {
                // 유효하지 않을 경우
                Toast.makeText(this, "비밀번호는 영어와 특수문자를 포함한 8~12자리여야 합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 비밀번호 확인란과 일치하는지 확인
            if (newPw != pwCheck) {
                // 비밀번호 확인이 일치하지 않는 경우
                Toast.makeText(this, "비밀번호와 비밀번호 확인이 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            showConfirmationDialog()
        }

    }

    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("비밀번호 변경이 완료되었습니다.")
            .setPositiveButton("확인") { dialog, which ->
                // 확인 선택 시 로그인 화면으로 이동
                // db에서 변경된 비밀번호로 update + 유저 아이디 찾아서 넣어야하는 코드 추가
                val intent = Intent(this, user_LogIn::class.java)
                startActivity(intent)
                // 비밀번호 변경 화면 종료
                finish()
            }
            .show()
    }

}