package com.example.villive.User_SignPage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
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
import com.example.villive.model.LoginResponse
import com.example.villive.model.SignUpRequestDto
import okhttp3.ResponseBody
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
        member_id.filters = arrayOf(noSpaceFilter)
        password.filters = arrayOf(noSpaceFilter)

        // Retrofit 객체 생성
        retrofitService = RetrofitService.getService(this)
        val logInRequestDtoAPI = retrofitService.create(LogInRequestDtoAPI::class.java)

        goLogin.setOnClickListener {
            val memberId = member_id.text.toString()
            val pwd = password.text.toString()

            // 로그인 요청 데이터 생성
            val logInRequestDto = LogInRequestDto(memberId, pwd)

            // 로그인 요청
            logInRequestDtoAPI.login(logInRequestDto).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful && response.body() != null) {
                        val token = response.body()?.string()
                        Log.d("LoginActivity", "Received Token: $token")
                        saveToken(token)

                        Toast.makeText(this@user_LogIn, "로그인 성공", Toast.LENGTH_LONG).show()

                        // 건물 인증 페이지로 이동
                        showConfirmationDialog()
                    } else {
                        Toast.makeText(this@user_LogIn, "로그인 실패: ${response.message()}", Toast.LENGTH_LONG).show()
                        Log.e("LoginActivity", "Response failed: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
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

