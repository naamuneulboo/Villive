package ood.villive_management.MGMT_main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.example.villive.Retrofit.RetrofitService
import okhttp3.ResponseBody
import ood.villive_management.Model.LogInRequestDto
import ood.villive_management.Model.LoginResponse
import ood.villive_management.R
import ood.villive_management.Retrofit.LogInRequestDtoAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class Login : AppCompatActivity() {
        private lateinit var retrofitService: Retrofit

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.user_log_in)

            val member_id = findViewById<EditText>(R.id.et_login_id)
            val password = findViewById<EditText>(R.id.et_login_pw)
            val goLogin = findViewById<Button>(R.id.btn_login)

            // Retrofit 객체 생성
            retrofitService = RetrofitService.getService(this)
            val logInRequestDtoAPI = retrofitService.create(LogInRequestDtoAPI::class.java)

            goLogin.setOnClickListener {
                val memberId = member_id.text.toString()
                val pwd = password.text.toString()

                // 로그인 요청 데이터 생성
                val logInRequestDto = LogInRequestDto(memberId, pwd)

                // 로그인 요청
                logInRequestDtoAPI.login(logInRequestDto).enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        if (response.isSuccessful && response.body() != null) {
                            val loginResponse = response.body()
                            val token = loginResponse?.token
                            val hasBuildingCode = loginResponse?.hasBuildingCode ?: false

                            Log.d("LoginActivity", "Received Token: $token")
                            saveToken(token)

                            Toast.makeText(this@Login, "로그인 성공", Toast.LENGTH_LONG).show()

                            if (hasBuildingCode) {
                                val intent = Intent(this@Login, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                showConfirmationDialog()
                            }
                        } else {
                            Toast.makeText(this@Login, "로그인 실패: ${response.message()}", Toast.LENGTH_LONG).show()
                            Log.e("LoginActivity", "Response failed: ${response.errorBody()?.string()}")
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(this@Login, "네트워크 오류: ${t.message}", Toast.LENGTH_LONG).show()
                        Log.e("LoginActivity", "Network error", t)
                    }
                })
            }
        }

        private fun showConfirmationDialog() {
            val builder = AlertDialog.Builder(this@Login)
            builder.setMessage("로그인이 완료되었습니다!\n관리자 페이지로 이동합니다!")
                .setPositiveButton("확인") { dialog, which ->
                    val intent = Intent(this@Login, MainActivity::class.java)
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