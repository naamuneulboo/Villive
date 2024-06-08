package ood.villive_management.MGMT_main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ood.villive_management.Retrofit.RetrofitService
import okhttp3.ResponseBody
import ood.villive_management.Model.NoticeRequestDto
import ood.villive_management.Model.NoticeResponseDto
import ood.villive_management.R
import ood.villive_management.Retrofit.NoticeRequestDtoAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddNotice : AppCompatActivity() {
    private lateinit var apiService: NoticeRequestDtoAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_notice)

            // Retrofit 객체 생성
            apiService = RetrofitService.getService(this).create(NoticeRequestDtoAPI::class.java)

            // View 초기화
            val etNoticeTitle = findViewById<EditText>(R.id.et_post_title)
            val etNoticeWrite = findViewById<EditText>(R.id.et_post_write)
            val btnAddNotice = findViewById<Button>(R.id.btn_add_noti)

            // 게시글 등록 버튼 클릭 리스너
            btnAddNotice.setOnClickListener {
                val title = etNoticeTitle.text.toString().trim()
                val contents = etNoticeWrite.text.toString().trim()

                if (title.isNotEmpty() && contents.isNotEmpty()) {
                    // 게시글 작성 내용이 있는 경우 API 호출
                    val noticeRequestDto = NoticeRequestDto(title, contents)
                    addNotice(noticeRequestDto)
                } else {
                    // 게시글 작성 내용이 없는 경우 사용자에게 알림
                    Toast.makeText(this, "공지사항 제목과 내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // 공지사항 등록을 위한 API 호출
        private fun addNotice(noticeRequestDto: NoticeRequestDto) {
            apiService.write(noticeRequestDto).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        // 게시글 등록 성공 시 사용자에게 알림
                        Toast.makeText(this@AddNotice, "게시글이 등록되었습니다.", Toast.LENGTH_SHORT).show()
                        finish() // 액티비티 종료
                    } else {
                        // 게시글 등록 실패 시 사용자에게 알림
                        Toast.makeText(this@AddNotice, "게시글 등록에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    // 네트워크 오류 등의 이유로 API 호출 실패 시 사용자에게 알림
                    Toast.makeText(this@AddNotice, "네트워크 오류: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }



}