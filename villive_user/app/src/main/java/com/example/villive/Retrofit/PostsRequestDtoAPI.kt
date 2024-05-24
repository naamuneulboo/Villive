package com.example.villive.Retrofit

import com.example.villive.model.PostsRequestDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface PostsRequestDtoAPI {
    @POST("/posts/write")
    fun write(@Body postsRequestDto: PostsRequestDto): Call<ResponseBody>
}