package com.example.villive.User_SignPage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.villive.R

class user_SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.user_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnSignUp = findViewById<Button>(R.id.btn_sign_up)
        btnSignUp.setOnClickListener {

            val etName = findViewById<EditText>(R.id.et_name)
            val etNickname = findViewById<EditText>(R.id.et_nickname)
            val etHomenum = findViewById<EditText>(R.id.et_home_num)

            val etId = findViewById<EditText>(R.id.et_id)
            val etPw = findViewById<EditText>(R.id.et_pw)
            val etPwCheck = findViewById<EditText>(R.id.et_pw_check)

            val name = etName.text.toString()
            val nickname = etNickname.text.toString()
            val homenum = etHomenum.text.toString()

            val id = etId.text.toString()
            val pw = etPw.text.toString()
            val pwCheck = etPwCheck.text.toString()

            // 공백
            if (id.trim().isEmpty() || pw.trim().isEmpty() || pwCheck.trim().isEmpty() || name.trim().isEmpty() || nickname.trim().isEmpty() || homenum.trim().isEmpty()) {
                // 공백이 있을 경우
                Toast.makeText(this, "모든 필드는 공백일 수 없습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 아이디 유효성 검사: 영어 6~10자리
            val idPattern = Regex("[a-zA-Z0-9]{6,12}")
            if (!idPattern.matches(id)) {
                // 유효하지 않을 경우
                Toast.makeText(this, "아이디는 영어와 숫자를 포함한 6~12자리여야 합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 비밀번호 유효성 검사: 영어와 특수문자 포함 8~12자리
            val pwPattern = Regex("(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9])(?=\\S+$).{8,12}")
            if (!pwPattern.matches(pw)) {
                // 유효하지 않을 경우
                Toast.makeText(this, "비밀번호는 영어와 특수문자를 포함한 8~12자리여야 합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 비밀번호 확인란과 일치하는지 확인
            if (pw != pwCheck) {
                // 비밀번호 확인이 일치하지 않는 경우
                Toast.makeText(this, "비밀번호와 비밀번호 확인이 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 닉네임 중복 여부


            // 모든 유효성 검사를 통과한 경우 회원가입 완료 다이얼로그 표시
            showConfirmationDialog()




        }
    }

    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("회원가입이 완료되었습니다!\n건물코드 인증 화면으로 이동하시겠습니까?")
            .setPositiveButton("예") { dialog, which ->
                // "예"를 선택한 경우 로그인 화면으로 이동
                // 이때 db에 쏙쏙 하면 될듯 ?!
                val intent = Intent(this@user_SignUp, user_BuildingAuth::class.java)
                startActivity(intent)
                // 더이상 회원가입 창은 필요 없으니 finish
                finish()
            }
            .show()
    }

}
