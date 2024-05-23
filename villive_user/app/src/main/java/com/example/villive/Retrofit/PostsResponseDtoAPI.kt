package com.example.villive.Retrofit


import com.example.villive.model.PostsResponseDto
import retrofit2.Call
import retrofit2.http.GET

interface PostsResponseDtoAPI {
    @GET("/posts/")
    fun getAllPostsResponseDto(): Call<List<PostsResponseDto>>
}
