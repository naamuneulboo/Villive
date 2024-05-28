package com.example.villive.Building_Issue

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.villive.Community_Write.Post_Detail_View
import com.example.villive.R
import com.example.villive.Retrofit.PostsResponseDtoAPI
import com.example.villive.Retrofit.RetrofitService
import com.example.villive.model.PostsResponseDto
import com.example.villive.Building_Issue.IssueAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Building_Issue : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var issueAdapter: IssueAdapter
    private lateinit var postsResponseDtoAPI: PostsResponseDtoAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.building_issue)

        recyclerView = findViewById(R.id.rv_posts_club)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val retrofit = RetrofitService.getService(this)
        postsResponseDtoAPI = retrofit.create(PostsResponseDtoAPI::class.java)

        getAllPosts()
    }

    private fun getAllPosts() {
        val call = postsResponseDtoAPI.getAllPostsResponseDto()
        call.enqueue(object : Callback<List<PostsResponseDto>> {
            override fun onResponse(call: Call<List<PostsResponseDto>>, response: Response<List<PostsResponseDto>>) {
                if (!response.isSuccessful) {
                    return
                }

                val postIds = response.body()?.map { it.id } ?: return
                getPostDetails(postIds)
            }

            override fun onFailure(call: Call<List<PostsResponseDto>>, t: Throwable) {
                // Handle failure
            }
        })
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
                                if (postDetail.postsLikeCnt ?: 0 >= 10) {  // 필터링 조건 추가
                                    postsDetails.add(postDetail)
                                }
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
        issueAdapter = IssueAdapter(postsDetails)
        recyclerView.adapter = issueAdapter

        issueAdapter.setOnItemClickListener(object : IssueAdapter.OnItemClickListener {
            override fun onItemClick(post: PostsResponseDto) {
                val intent = Intent(this@Building_Issue, Post_Detail_View::class.java).apply {
                    putExtra("POST_ID", post.id.toString())
                }
                startActivity(intent)
            }
        })
    }
}