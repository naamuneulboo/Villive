package com.example.villive.Community

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.villive.Community_Write.Post_Detail_View
import com.example.villive.Community_Write.Post_Share
import com.example.villive.Post_model_adapter.PostsAdapter
import com.example.villive.R
import com.example.villive.Retrofit.PostsResponseDtoAPI
import com.example.villive.Retrofit.RetrofitService
import com.example.villive.model.PostsResponseDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Community_Share : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var postsAdapter: PostsAdapter
    private lateinit var postsResponseDtoAPI: PostsResponseDtoAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.community_share)

        recyclerView = findViewById(R.id.rv_posts_share)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Retrofit 객체 가져오기
        val retrofit = RetrofitService.getService(this)
        postsResponseDtoAPI = retrofit.create(PostsResponseDtoAPI::class.java)

        // 게시글 ID 목록 가져오기
        val call = postsResponseDtoAPI.getAllPostsResponseDto()
        call.enqueue(object : Callback<List<PostsResponseDto>> {
            override fun onResponse(call: Call<List<PostsResponseDto>>, response: Response<List<PostsResponseDto>>) {
                if (!response.isSuccessful) {
                    // Handle error
                    return
                }

                // 나눔 카테고리의 게시글 ID 목록 필터링
                val postIds = response.body()
                    ?.filter { it.category == PostsResponseDto.Category.나눔 }
                    ?.map { it.id }
                    ?: return

                // 게시글의 상세 정보
                getPostDetails(postIds)
            }

            override fun onFailure(call: Call<List<PostsResponseDto>>, t: Throwable) {
                // Handle failure
            }
        })

        val write_post = findViewById<Button>(R.id.btn_write_post)
        write_post.setOnClickListener {
            val intent = Intent(this, Post_Share::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getPostDetails(postIds: List<Long?>) {
        val postsDetails = mutableListOf<PostsResponseDto>()
        var processedCount = 0

        postIds.forEach { id ->
            id?.let {
                postsResponseDtoAPI.getPostById(it).enqueue(object : Callback<PostsResponseDto> {
                    override fun onResponse(call: Call<PostsResponseDto>, response: Response<PostsResponseDto>) {
                        if (response.isSuccessful) {
                            response.body()?.let { postDetail ->
                                postsDetails.add(postDetail)
                            }
                        }
                        processedCount++
                        if (processedCount == postIds.size) {
                            updateRecyclerView(postsDetails)
                        }
                    }

                    override fun onFailure(call: Call<PostsResponseDto>, t: Throwable) {
                        processedCount++
                        if (processedCount == postIds.size) {
                            updateRecyclerView(postsDetails)
                        }
                    }
                })
            } ?: run {
                processedCount++
                if (processedCount == postIds.size) {
                    updateRecyclerView(postsDetails)
                }
            }
        }
    }

    private fun updateRecyclerView(postsDetails: List<PostsResponseDto>) {
        postsAdapter = PostsAdapter(postsDetails)
        recyclerView.adapter = postsAdapter

        postsAdapter.setOnItemClickListener(object : PostsAdapter.OnItemClickListener {
            override fun onItemClick(post: PostsResponseDto) {
                val intent = Intent(this@Community_Share, Post_Detail_View::class.java).apply {
                    putExtra("POST_ID", post.id.toString())
                }
                startActivity(intent)
            }
        })
    }
}