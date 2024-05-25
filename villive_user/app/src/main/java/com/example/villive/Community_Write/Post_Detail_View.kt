package com.example.villive.Community_Write

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.villive.R
import com.example.villive.Retrofit.PostsResponseDtoAPI
import com.example.villive.Retrofit.RetrofitService
import com.example.villive.model.PostsResponseDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Post_Detail_View : AppCompatActivity() {
    private lateinit var titleTextView: TextView
    private lateinit var contentsTextView: TextView
    private lateinit var writerTextView: TextView
    private lateinit var createDateTextView: TextView
    private lateinit var postId: String // 게시글의 ID를 저장하기 위한 변수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_detail_view)

        titleTextView = findViewById(R.id.post_title)
        contentsTextView = findViewById(R.id.post_contents)
        writerTextView = findViewById(R.id.post_writer)
        createDateTextView = findViewById(R.id.post_create_date)

        // Intent로부터 게시글의 ID를 가져옴
        postId = intent.getStringExtra("POST_ID").toString()

        // 게시글의 ID를 사용하여 서버에서 해당하는 게시글의 상세 정보를 가져옴
        val retrofit = RetrofitService.getService(this)
        val postsResponseDtoAPI = retrofit.create(PostsResponseDtoAPI::class.java)

        postsResponseDtoAPI.getPostById(postId.toLong()).enqueue(object : Callback<PostsResponseDto> {
            override fun onResponse(call: Call<PostsResponseDto>, response: Response<PostsResponseDto>) {
                if (response.isSuccessful) {
                    val post = response.body()
                    // 가져온 데이터를 각각의 TextView에 설정
                    titleTextView.text = post?.title
                    contentsTextView.text = post?.contents
                    writerTextView.text = post?.writer
                    createDateTextView.text = post?.createDate
                } else {
                    // 실패 시 처리
                }
            }

            override fun onFailure(call: Call<PostsResponseDto>, t: Throwable) {
                // 실패 시 처리
            }
        })
    }
}