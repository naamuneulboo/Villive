package com.example.villive.User_SignPage

import android.content.Context
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
import com.example.villive.MainActivity
import com.example.villive.R
import com.example.villive.Retrofit.LogInRequestDtoAPI
import com.example.villive.Retrofit.RetrofitService
import com.example.villive.Retrofit.SignUpRequestDtoAPI
import com.example.villive.model.LogInRequestDto
import com.example.villive.model.SignUpRequestDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class user_LogIn : AppCompatActivity() {
    private val retrofitService = RetrofitService.getService()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_log_in)


        val password = findViewById<EditText>(R.id.et_login_id)
        val member_id = findViewById<EditText>(R.id.et_login_pw)
        val goLogin = findViewById<Button>(R.id.btn_login)

        // Retrofit 객체 생성
        val logInRequestDtoAPI = retrofitService.create(LogInRequestDtoAPI::class.java)



        goLogin.setOnClickListener {

            val member_id = member_id.text.toString()
            val password = password.text.toString()

            // 로그인 요청 데이터 생성
            val logInRequestDto = LogInRequestDto().apply {
                this.password = password
                this.member_id = member_id
            }

            // 로그인 요청
            logInRequestDtoAPI.login(logInRequestDto).enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@user_LogIn, "로그인 성공", Toast.LENGTH_LONG).show()
                        // 회원가입 성공 후의 처리

                        // 토큰을 저장
                        val token = response.headers()["Authorization"]
                        saveToken(token)

                        // 건물 인증 페이지로 이동
                        showConfirmationDialog()
                    } else {
                        Toast.makeText(this@user_LogIn, "로그인 실패", Toast.LENGTH_LONG).show()
                        // 회원가입 실패 후의 처리
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    Toast.makeText(this@user_LogIn, "네트워크 오류", Toast.LENGTH_LONG).show()
                    // 네트워크 오류 시의 처리
                }
            })

        }
    }

    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(this@user_LogIn)
        builder.setMessage("로그인이 완료되었습니다!\n건물코드 인증 화면으로 이동하시겠습니까?")
            .setPositiveButton("예") { dialog, which ->
                val intent = Intent(this@user_LogIn, user_BuildingAuth::class.java)
                startActivity(intent)
                finish()
            }
            .show()
    }
    private fun saveToken(token: String?) {
        val sharedPreferences = getSharedPreferences("auth", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("token", token)
        editor.apply()
    }
}