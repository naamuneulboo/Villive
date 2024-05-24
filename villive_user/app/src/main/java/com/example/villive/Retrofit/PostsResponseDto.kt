package com.example.villive.Retrofit


import com.example.villive.model.PostsResponseDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PostsResponseDtoAPI {
    @GET("/posts/")
    fun getAllPostsResponseDto(): Call<List<PostsResponseDto>>

    @GET("/posts/{id}")
    fun getPostById(@Path("id") id: Long): Call<PostsResponseDto>
}