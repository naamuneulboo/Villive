package com.example.villive.ConfigurationPage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.villive.R
import com.example.villive.Retrofit.RetrofitService
import com.example.villive.Retrofit.UpdatePwdRequestDtoAPI
import com.example.villive.User_SignPage.user_LogIn
import com.example.villive.model.UpdatePwdRequestDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PasswordChangeActivity : AppCompatActivity() {
    private lateinit var updatePwdRequestDtoAPI: UpdatePwdRequestDtoAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.password_change)
        initializeComponents()
        updatePwdRequestDtoAPI = RetrofitService.getService(this).create(UpdatePwdRequestDtoAPI::class.java)
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

            // 서버에 비밀번호 변경 요청 보내기
            val updatePwdRequestDto = UpdatePwdRequestDto(nowPw, newPw)
            val call = updatePwdRequestDtoAPI.updatePassword(updatePwdRequestDto)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        showConfirmationDialog()
                    } else {
                        Toast.makeText(this@PasswordChangeActivity, "서버 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    // 통신 실패 시 처리
                    Toast.makeText(this@PasswordChangeActivity, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("비밀번호 변경이 완료되었습니다.")
            .setPositiveButton("확인") { dialog, which ->
                // 확인 선택 시 로그아웃 처리 및 앱의 시작 화면으로 이동
                logoutAndRedirectToLogin()
            }
            .setCancelable(false)
            .show()
    }

    private fun logoutAndRedirectToLogin() {
        // SharedPreferences에서 토큰을 삭제
        // 토큰 삭제하고 새 거 줘야되는거같음
        // 아니그게문제가아니고 변경한 비번으로 로그인이 안됨
        val sharedPreferences = getSharedPreferences("auth", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("token")
        editor.apply()

        // 로그인 화면으로 이동하고, 이전에 방문한 화면으로 돌아갈 수 없도록 함
        val intent = Intent(this, user_LogIn::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finishAffinity()
    }

}
