package com.example.villive.User_SignPage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.villive.MainActivity
import com.example.villive.R
import com.example.villive.Retrofit.LogInRequestDtoAPI
import com.example.villive.Retrofit.RetrofitService
import com.example.villive.model.LogInRequestDto
import com.example.villive.model.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class user_LogIn : AppCompatActivity() {
    private lateinit var retrofitService: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_log_in)

        val member_id = findViewById<EditText>(R.id.et_login_id)
        val password = findViewById<EditText>(R.id.et_login_pw)
        val goLogin = findViewById<Button>(R.id.btn_login)

        val noHangulRegex = Regex("[^ㄱ-ㅎㅏ-ㅣ가-힣]+")

        val noHangulFilter = InputFilter { source, start, end, dest, dstart, dend ->
            if (source != null && !source.toString().matches(noHangulRegex)) {
                ""
            } else {
                null
            }
        }

        val noSpaceFilter = InputFilter { source, start, end, dest, dstart, dend ->
            for (i in start until end) {
                if (Character.isWhitespace(source[i])) {
                    return@InputFilter ""
                }
            }
            null
        }

        member_id.filters = arrayOf(noHangulFilter, noSpaceFilter)
        password.filters = arrayOf(noHangulFilter, noSpaceFilter)

        retrofitService = RetrofitService.getService(this)
        val logInRequestDtoAPI = retrofitService.create(LogInRequestDtoAPI::class.java)

        goLogin.setOnClickListener {
            val memberId = member_id.text.toString()
            val pwd = password.text.toString()

            val logInRequestDto = LogInRequestDto(member_id = memberId, password = pwd)

            logInRequestDtoAPI.login(logInRequestDto).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful && response.body() != null) {
                        val loginResponse = response.body()
                        val token = loginResponse?.token
                        val hasBuildingCode = loginResponse?.hasBuildingCode ?: false

                        Log.d("LoginActivity", "Received Token: $token")
                        saveToken(token)

                        Toast.makeText(this@user_LogIn, "로그인 성공", Toast.LENGTH_LONG).show()

                        if (hasBuildingCode) {
                            val intent = Intent(this@user_LogIn, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            showConfirmationDialog()
                        }
                    } else {
                        Toast.makeText(this@user_LogIn, "로그인 실패: ${response.message()}", Toast.LENGTH_LONG).show()
                        Log.e("LoginActivity", "Response failed: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@user_LogIn, "네트워크 오류: ${t.message}", Toast.LENGTH_LONG).show()
                    Log.e("LoginActivity", "Network error", t)
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
        if (token != null) {
            val sharedPreferences = getSharedPreferences("auth", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("token", token)
            editor.apply()
        } else {
            Toast.makeText(this, "토큰 저장 실패", Toast.LENGTH_SHORT).show()
        }
    }
}
