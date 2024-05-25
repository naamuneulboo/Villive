package com.example.villive.Community_Write

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.villive.R
import com.example.villive.Retrofit.ComplainRequestDtoAPI
import com.example.villive.Retrofit.RetrofitService
import com.example.villive.model.ComplainRequestDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Post_Complain : AppCompatActivity() {
    private lateinit var apiService: ComplainRequestDtoAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_complain)

        // Retrofit 객체 생성
        apiService = RetrofitService.getService(this).create(ComplainRequestDtoAPI::class.java)

        // View 초기화
        val etComplainTitle = findViewById<EditText>(R.id.et_post_title)
        val etComplainWrite = findViewById<EditText>(R.id.et_post_write)
        val btnAddComplain = findViewById<Button>(R.id.btn_add_post)
        val spinnerComplainType = findViewById<Spinner>(R.id.sp_item)

        // 스피너 초기화
        val complainTypes = arrayOf("기계고장", "공동시설", "환경개선", "건의사항", "기타")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, complainTypes)
        spinnerComplainType.adapter = adapter

        // 버튼 클릭 리스너 설정
        btnAddComplain.setOnClickListener {
            val title = etComplainTitle.text.toString().trim()
            val contents = etComplainWrite.text.toString().trim()
            val type = spinnerComplainType.selectedItem.toString()

            if (title.isNotEmpty() && contents.isNotEmpty()) {
                // 게시글 작성 내용이 있는 경우 API 호출
                val complainRequestDto = ComplainRequestDto(type, contents, title, "접수")
                addComplain(complainRequestDto)
            } else {
                // 게시글 작성 내용이 없는 경우 사용자에게 알림
                Toast.makeText(this, "게시글 제목과 내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 게시글 등록을 위한 API 호출
    private fun addComplain(complainRequestDto: ComplainRequestDto) {
        apiService.add(complainRequestDto).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    // 게시글 등록 성공 시 사용자에게 알림
                    Toast.makeText(this@Post_Complain, "민원 게시글이 등록되었습니다.", Toast.LENGTH_SHORT).show()
                    finish() // 액티비티 종료
                } else {
                    // 게시글 등록 실패 시 사용자에게 알림
                    Toast.makeText(this@Post_Complain, "민원 게시글 등록에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // 네트워크 오류 등의 이유로 API 호출 실패 시 사용자에게 알림
                Toast.makeText(this@Post_Complain, "네트워크 오류: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}