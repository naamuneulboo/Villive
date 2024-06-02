package com.example.villive.ConfigurationPage
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.villive.R
import com.example.villive.Retrofit.DeleteMemRequestDtoAPI
import com.example.villive.Retrofit.RetrofitService
import com.example.villive.model.MsgResponseDto
import com.example.villive.model.UnregisterRequestDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UnregisterActivity : AppCompatActivity() {

    private lateinit var deleteMemRequestDtoAPI: DeleteMemRequestDtoAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.unregister)

        val passwordEditText = findViewById<EditText>(R.id.editTextPassword)
        val unregisterButton = findViewById<Button>(R.id.buttonUnregister)

        // Retrofit 객체 생성
        val retrofit = RetrofitService.getService(this)
        deleteMemRequestDtoAPI = retrofit.create(DeleteMemRequestDtoAPI::class.java)

        unregisterButton.setOnClickListener {
            val password = passwordEditText.text.toString()

            // 입력된 비밀번호를 사용하여 회원 탈퇴 요청 생성
            val unregisterRequestDto = UnregisterRequestDto(password)

            // 회원 탈퇴 요청 수행
            deleteMemRequestDtoAPI.deleteMember(unregisterRequestDto).enqueue(object : Callback<MsgResponseDto> {
                override fun onResponse(call: Call<MsgResponseDto>, response: Response<MsgResponseDto>) {
                    if (response.isSuccessful) {
                        // 회원 탈퇴 성공
                        Toast.makeText(this@UnregisterActivity, "회원 탈퇴되었습니다.", Toast.LENGTH_SHORT).show()
                        finish() // 현재 액티비티 종료
                    } else {
                        // 회원 탈퇴 실패
                        Toast.makeText(this@UnregisterActivity, "회원 탈퇴 실패: ${response.message()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<MsgResponseDto>, t: Throwable) {
                    // 네트워크 오류 등의 실패 처리
                    Toast.makeText(this@UnregisterActivity, "네트워크 오류: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}