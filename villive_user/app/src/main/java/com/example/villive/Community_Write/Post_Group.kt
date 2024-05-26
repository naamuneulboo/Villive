package com.example.villive.Community_Write

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.villive.R
import com.example.villive.Retrofit.PostsRequestDtoAPI
import com.example.villive.Retrofit.RetrofitService
import com.example.villive.model.PostsRequestDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Post_Group : AppCompatActivity() {
    private lateinit var apiService: PostsRequestDtoAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_group)

        // Retrofit 객체 생성
        apiService = RetrofitService.getService(this).create(PostsRequestDtoAPI::class.java)

        // View 초기화
        val etPostTitle = findViewById<EditText>(R.id.et_post_title)
        val etPostWrite = findViewById<EditText>(R.id.et_post_write)
        val btnAddPost = findViewById<Button>(R.id.btn_add_post)

        // 게시글 등록 버튼 클릭 리스너
        btnAddPost.setOnClickListener {
            val title = etPostTitle.text.toString().trim()
            val contents = etPostWrite.text.toString().trim()
            val category = "단체" // 카테고리는 일단 하드코딩으로 설정

            if (title.isNotEmpty() && contents.isNotEmpty()) {
                // 게시글 작성 내용이 있는 경우 API 호출
                val postsRequestDto = PostsRequestDto(title, contents, category)
                addPost(postsRequestDto)
            } else {
                // 게시글 작성 내용이 없는 경우 사용자에게 알림
                Toast.makeText(this, "게시글 제목과 내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 게시글 등록을 위한 API 호출
    private fun addPost(postsRequestDto: PostsRequestDto) {
        apiService.write(postsRequestDto).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    // 게시글 등록 성공 시 사용자에게 알림
                    Toast.makeText(this@Post_Group, "게시글이 등록되었습니다.", Toast.LENGTH_SHORT).show()
                    finish() // 액티비티 종료
                } else {
                    // 게시글 등록 실패 시 사용자에게 알림
                    Toast.makeText(this@Post_Group, "게시글 등록에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // 네트워크 오류 등의 이유로 API 호출 실패 시 사용자에게 알림
                Toast.makeText(this@Post_Group, "네트워크 오류: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}