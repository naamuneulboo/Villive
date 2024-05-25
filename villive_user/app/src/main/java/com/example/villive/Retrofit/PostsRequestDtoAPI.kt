package com.example.villive.Retrofit

import com.example.villive.model.PostsRequestDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PostsRequestDtoAPI {
    @POST("/posts/write")  // 게시글 작성
    fun write(@Body postsRequestDto: PostsRequestDto): Call<ResponseBody>

    @PUT("/posts/{id}")  // 게시글 수정
    fun updatePost(@Path("id") id: Long, @Body postsRequestDto: PostsRequestDto): Call<ResponseBody>

}