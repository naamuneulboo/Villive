package com.example.villive.User_SignPage

import android.content.Intent
import android.os.Bundle
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
    private val retrofitService = RetrofitService.getService()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_sign_up)

        val inputEditID = findViewById<EditText>(R.id.et_id)
        val inputEditPassword = findViewById<EditText>(R.id.et_pw)
        val inputEditName = findViewById<EditText>(R.id.et_name)
        val inputEditNickname = findViewById<EditText>(R.id.et_nickname)
        val inputPasswordCheck = findViewById<EditText>(R.id.et_pw_check)
        val buttonSave = findViewById<Button>(R.id.btn_sign_up)

        // Retrofit 객체 생성
        val signUpRequestDtoAPI = retrofitService.create(SignUpRequestDtoAPI::class.java)


        buttonSave.setOnClickListener {
            val member_id = inputEditID.text.toString()
            val password = inputEditPassword.text.toString()
            val name = inputEditName.text.toString()
            val nickname = inputEditNickname.text.toString()

            // 비밀번호 확인
            val passwordCheck = inputPasswordCheck.text.toString()
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