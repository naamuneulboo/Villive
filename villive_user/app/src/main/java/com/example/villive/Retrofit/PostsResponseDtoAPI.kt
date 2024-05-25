package com.example.villive.Retrofit


import com.example.villive.model.PostsResponseDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PostsResponseDtoAPI {
    @GET("/posts/")  // 모든 게시글 조회
    fun getAllPostsResponseDto(): Call<List<PostsResponseDto>>

    @GET("/posts/{id}")  // 각각의 게시글 조회 (상세보기)
    fun getPostById(@Path("id") id: Long): Call<PostsResponseDto>
}