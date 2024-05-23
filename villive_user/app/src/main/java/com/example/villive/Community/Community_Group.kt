package com.example.villive.Community

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.villive.Post_model_adapter.PostsAdapter
import com.example.villive.R
import com.example.villive.Retrofit.PostsResponseDtoAPI
import com.example.villive.Retrofit.RetrofitService
import com.example.villive.model.PostsResponseDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Community_Group : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var postsAdapter: PostsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.community_group)

        recyclerView = findViewById(R.id.rv_posts_group)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val retrofitService = RetrofitService()
        val postsResponseDtoAPI = retrofitService.retrofit.create(PostsResponseDtoAPI::class.java)

        val call = postsResponseDtoAPI.getAllPostsResponseDto()
        call.enqueue(object : Callback<List<PostsResponseDto>> {
            override fun onResponse(call: Call<List<PostsResponseDto>>, response: Response<List<PostsResponseDto>>) {
                if (!response.isSuccessful) {
                    // Handle error
                    return
                }

                val postsResponseDtos = response.body()?.filter { it.category == PostsResponseDto.Category.단체 } ?: return
                postsAdapter = PostsAdapter(postsResponseDtos)
                recyclerView.adapter = postsAdapter
            }

            override fun onFailure(call: Call<List<PostsResponseDto>>, t: Throwable) {
                // Handle failure
            }
        })
    }
}
