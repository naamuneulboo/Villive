package com.example.villive.ConfigurationPage

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.villive.R
import com.example.villive.Retrofit.RetrofitService
import com.example.villive.Retrofit.UpdateNicknameDtoAPI
import com.example.villive.model.UpdateNicknameDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NicknameChangeActivity : AppCompatActivity() {

    private lateinit var updateNicknameDtoAPI: UpdateNicknameDtoAPI
    private lateinit var checkNicknameExistenceAPI: UpdateNicknameDtoAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nickname_change)
        initializeComponents()
    }

    private fun initializeComponents() {
        val inputEditNickname = findViewById<EditText>(R.id.editTextNickname)
        val buttonCheck = findViewById<Button>(R.id.buttonCheck)

        updateNicknameDtoAPI = RetrofitService.getService(this).create(UpdateNicknameDtoAPI::class.java)
        checkNicknameExistenceAPI = RetrofitService.getService(this).create(UpdateNicknameDtoAPI::class.java)

        buttonCheck.setOnClickListener {
            val nickname = inputEditNickname.text.toString()

            if (nickname.trim().isEmpty()) {
                // 닉네임이 공백인 경우
                Toast.makeText(this, "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 닉네임 중복 검사 요청 보내기
            val call = checkNicknameExistenceAPI.checkNicknameExistence(nickname)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()?.string()
                        if (responseBody != null && responseBody.toBoolean()) {
                            // 닉네임이 이미 존재하는 경우
                            Toast.makeText(this@NicknameChangeActivity, "이미 사용 중인 닉네임입니다.", Toast.LENGTH_SHORT).show()
                        } else {
                            // 닉네임이 중복되지 않는 경우 닉네임 업데이트 요청 보내기
                            val updateNicknameDto = UpdateNicknameDto(nickname)
                            val updateCall = updateNicknameDtoAPI.updateNickname(updateNicknameDto)
                            updateCall.enqueue(object : Callback<ResponseBody> {
                                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                                    if (response.isSuccessful) {
                                        // 성공적으로 서버로부터 응답을 받은 경우
                                        Toast.makeText(this@NicknameChangeActivity, "닉네임이 성공적으로 업데이트되었습니다.", Toast.LENGTH_SHORT).show()
                                        // 닉네임 변경 완료 후 이동할 화면 등을 여기에 구현합니다.
                                    } else {
                                        // 서버 오류 등으로 응답을 받지 못한 경우
                                        Toast.makeText(this@NicknameChangeActivity, "서버 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                                    }
                                }

                                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                    // 통신 실패 시 처리
                                    Toast.makeText(this@NicknameChangeActivity, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                                }
                            })
                        }
                    } else {
                        // 서버 오류 등으로 응답을 받지 못한 경우
                        Toast.makeText(this@NicknameChangeActivity, "서버 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    // 통신 실패 시 처리
                    Toast.makeText(this@NicknameChangeActivity, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
