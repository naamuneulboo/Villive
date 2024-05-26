package com.example.villive.ConfigurationPage

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.villive.R
import com.example.villive.Retrofit.DeleteMemRequestDtoAPI
import com.example.villive.Retrofit.RetrofitService
import com.example.villive.model.MsgResponseDto
import com.example.villive.model.UnregisterRequestDto
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
        val editPw = findViewById<EditText>(R.id.editTextPassword)

        btnUnregister.setOnClickListener {
            val pw = editPw.text.toString().trim()

            if (pw.isEmpty()) {
                Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 회원 탈퇴 요청 보내기
            val unregisterRequestDto = UnregisterRequestDto(pw)
            deleteMember(unregisterRequestDto)
        }
    }

    private fun deleteMember(unregisterRequestDto: UnregisterRequestDto) {
        val call = deleteMemRequestAPI.deleteMember(unregisterRequestDto)
        call.enqueue(object : Callback<MsgResponseDto> {
            override fun onResponse(call: Call<MsgResponseDto>, response: Response<MsgResponseDto>) {
                if (response.isSuccessful) {
                    val msgResponseDto = response.body()
                    if (msgResponseDto != null) {
                        Toast.makeText(this@UnregisterActivity, msgResponseDto.msg, Toast.LENGTH_SHORT).show()
                        if (msgResponseDto.statusCode == 200) {
                            // 회원 탈퇴 성공
                            // 추가적인 처리가 필요하다면 여기에 작성
                            finish()
                        }
                    }
                } else {
                    Toast.makeText(this@UnregisterActivity, "서버 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MsgResponseDto>, t: Throwable) {
                Toast.makeText(this@UnregisterActivity, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

private fun <T> Call<T>.enqueue(callback: Callback<MsgResponseDto>) {
    TODO("Not yet implemented")
}
