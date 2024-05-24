package com.example.villive.Community

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.villive.Community_Write.Post_Club
import com.example.villive.Post_model_adapter.PostsAdapter
import com.example.villive.R
import com.example.villive.Retrofit.PostsResponseDtoAPI
import com.example.villive.Retrofit.RetrofitService
import com.example.villive.model.PostsResponseDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Community_Club : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var postsAdapter: PostsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.community_club)

        recyclerView = findViewById(R.id.rv_posts_club)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Retrofit 객체 가져오기
        val retrofit = RetrofitService.getService(this) // 여기에서 context 전달
        val postsResponseDtoAPI = retrofit.create(PostsResponseDtoAPI::class.java)

        val call = postsResponseDtoAPI.getAllPostsResponseDto()
        call.enqueue(object : Callback<List<PostsResponseDto>> {
            override fun onResponse(call: Call<List<PostsResponseDto>>, response: Response<List<PostsResponseDto>>) {
                if (!response.isSuccessful) {
                    // Handle error
                    return
                }

                val postsResponseDtos = response.body()?.filter { it.category == PostsResponseDto.Category.동호회 } ?: return
                postsAdapter = PostsAdapter(postsResponseDtos)
                recyclerView.adapter = postsAdapter

                // 아이템 클릭 리스너 설정
                postsAdapter.setOnItemClickListener(object : PostsAdapter.OnItemClickListener {
                    override fun onItemClick(post: PostsResponseDto) {
                        val intent = Intent(this@Community_Club, Post_Detail_View::class.java).apply {
                            putExtra("POST_ID", post.id)
                            putExtra("POST_TITLE", post.title)
                            putExtra("POST_CONTENTS", post.contents)
                            putExtra("POST_WRITER", post.writer)
                            putExtra("POST_CREATE_DATE", post.createDate)
                        }
                        startActivity(intent)
                    }
                })
            }

            override fun onFailure(call: Call<List<PostsResponseDto>>, t: Throwable) {
                // Handle failure
            }
        })

        val write_post = findViewById<Button>(R.id.btn_write_post)

        write_post.setOnClickListener {
            val intent = Intent(this, Post_Club::class.java)
            startActivity(intent)
        }
    }
}