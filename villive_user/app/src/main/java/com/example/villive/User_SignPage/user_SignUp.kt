package com.example.villive.User_SignPage

import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.villive.R
import com.example.villive.Retrofit.RetrofitService
import com.example.villive.Retrofit.SignUpRequestDtoAPI
import com.example.villive.model.SignUpRequestDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class user_SignUp : AppCompatActivity() {
    private val retrofitService = RetrofitService.getService(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_sign_up)

        val inputEditID = findViewById<EditText>(R.id.et_id)
        val inputEditPassword = findViewById<EditText>(R.id.et_pw)
        val inputEditName = findViewById<EditText>(R.id.et_name)
        val inputEditNickname = findViewById<EditText>(R.id.et_nickname)
        val inputPasswordCheck = findViewById<EditText>(R.id.et_pw_check)
        val buttonSave = findViewById<Button>(R.id.btn_sign_up)

        // 스페이스 입력 비활
        val noSpaceFilter = InputFilter { source, start, end, dest, dstart, dend ->
            for (i in start until end) {
                if (Character.isWhitespace(source[i])) {
                    return@InputFilter ""
                }
            }
            null
        }

        // EditText에 스페이스 비활 적용
        inputEditID.filters = arrayOf(noSpaceFilter)
        inputEditPassword.filters = arrayOf(noSpaceFilter)
        inputEditName.filters = arrayOf(noSpaceFilter)
        inputEditNickname.filters = arrayOf(noSpaceFilter)
        inputPasswordCheck.filters = arrayOf(noSpaceFilter)


        // Retrofit 객체 생성
        val signUpRequestDtoAPI = retrofitService.create(SignUpRequestDtoAPI::class.java)


        buttonSave.setOnClickListener {
            val member_id = inputEditID.text.toString()
            val password = inputEditPassword.text.toString()
            val name = inputEditName.text.toString()
            val nickname = inputEditNickname.text.toString()
            val passwordCheck = inputPasswordCheck.text.toString()

            // 공백
            if (member_id.trim().isEmpty() || password.trim().isEmpty() || name.trim().isEmpty() || nickname.trim().isEmpty() || passwordCheck.trim().isEmpty()) {
                // 공백이 있을 경우
                Toast.makeText(this, "모든 필드는 공백일 수 없습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 아이디 유효성 검사: 영어 6~10자리
            val idPattern = Regex("[a-zA-Z0-9]{6,12}")
            if (!idPattern.matches(member_id)) {
                // 유효하지 않을 경우
                Toast.makeText(this, "아이디는 영어와 숫자를 포함한 6~12자리여야 합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 비밀번호 유효성 검사: 영어와 특수문자 포함 8~12자리
            val pwPattern = Regex("(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9])(?=\\S+$).{8,12}")
            if (!pwPattern.matches(password)) {
                // 유효하지 않을 경우
                Toast.makeText(this, "비밀번호는 영어와 특수문자를 포함한 8~12자리여야 합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 비밀번호 확인
            if (password != passwordCheck) {
                Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            // 회원가입 요청 데이터 생성
            val signUpRequestDto = SignUpRequestDto().apply {
                this.member_id = member_id
                this.password = password
                this.name = name
                this.nickname = nickname
            }

            // 회원가입 요청
            signUpRequestDtoAPI.join(signUpRequestDto).enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@user_SignUp, "회원가입 성공", Toast.LENGTH_LONG).show()
                        // 회원가입 성공 후의 처리

                        // 건물 인증 페이지로 이동
                        showConfirmationDialog()
                    } else {
                        Toast.makeText(this@user_SignUp, "회원가입 실패", Toast.LENGTH_LONG).show()
                        // 회원가입 실패 후의 처리
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    Toast.makeText(this@user_SignUp, "네트워크 오류", Toast.LENGTH_LONG).show()
                    // 네트워크 오류 시의 처리
                }
            })
        }
    }

    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(this@user_SignUp)
        builder.setMessage("회원가입이 완료되었습니다!\n로그인 화면으로 이동하시겠습니까?")
            .setPositiveButton("예") { dialog, which ->
                val intent = Intent(this@user_SignUp, user_LogIn::class.java)
                startActivity(intent)
                finish()
            }
            .show()
    }
}