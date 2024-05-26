package com.example.villive.ConfigurationPage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.villive.R
import com.example.villive.Retrofit.DeleteMemRequestDtoAPI
import com.example.villive.Retrofit.RetrofitService
import com.example.villive.User_SignPage.StartPage
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

        deleteMemRequestAPI = RetrofitService.getService(this).create(DeleteMemRequestDtoAPI::class.java)

        val btnUnregister = findViewById<Button>(R.id.buttonUnregister)
        btnUnregister.setOnClickListener {
            val editPw = findViewById<EditText>(R.id.editTextPassword)
            val pw = editPw.text.toString().trim()

            if (pw.isEmpty()) {
                Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 회원 탈퇴 요청 보내기
            val deleteMemRequestDto = DeleteMemRequestDto(pw)
            deleteMember(deleteMemRequestDto)
        }
    }

    private fun deleteMember(deleteMemRequestDto: DeleteMemRequestDto) {
        val call = deleteMemRequestAPI.deleteMember(deleteMemRequestDto)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    // 회원 탈퇴 성공 시
                    Toast.makeText(this@UnregisterActivity, "회원 탈퇴가 완료되었습니다.", Toast.LENGTH_SHORT).show()
                    moveToStartPage()
                } else {
                    // 서버 응답 실패 시
                    Toast.makeText(this@UnregisterActivity, "서버 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // 통신 실패 시 처리
                Toast.makeText(this@UnregisterActivity, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun moveToStartPage() {
        val intent = Intent(this, StartPage::class.java)
        startActivity(intent)
        finish()
    }
}
