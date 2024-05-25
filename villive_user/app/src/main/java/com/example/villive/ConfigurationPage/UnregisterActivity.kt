// UnregisterActivity.kt
package com.example.villive.ConfigurationPage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.villive.R
import com.example.villive.Retrofit.DeleteMemRequestDtoAPI
import com.example.villive.Retrofit.RetrofitService
import com.example.villive.User_SignPage.user_SignUp
import com.example.villive.model.DeleteMemRequestDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UnregisterActivity : AppCompatActivity() {
    private lateinit var deleteMemRequestAPI: DeleteMemRequestDtoAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.unregister)
        initializeComponents()
        deleteMemRequestAPI = RetrofitService.getService(this).create(DeleteMemRequestDtoAPI::class.java)
    }

    fun initializeComponents() {
        val editPw = findViewById<EditText>(R.id.editTextPassword)
        val btnUnregister = findViewById<Button>(R.id.buttonUnregister)

        btnUnregister.setOnClickListener {
            val pw = editPw.text.toString()

            if (pw.trim().isEmpty()) {
                // 비밀번호가 공백인 경우
                Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 서버에 회원 탈퇴 요청 보내기
            val deleteMemRequestDto = DeleteMemRequestDto(pw)
            val call = deleteMemRequestAPI.deleteMember(deleteMemRequestDto)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        showConfirmationDialog()
                    } else {
                        Toast.makeText(this@UnregisterActivity, "서버 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    // 통신 실패 시 처리
                    Toast.makeText(this@UnregisterActivity, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("회원탈퇴를 하시겠습니까?\n회원탈퇴 시 복구할 수 없습니다.")
            .setPositiveButton("예") { dialog, which ->
                // "예"를 선택한 경우 회원가입 화면으로 이동
                val intent = Intent(this, user_SignUp::class.java)
                startActivity(intent)
                // 회원탈퇴화면 finish
                finish()
            }
            .show()
    }
}
