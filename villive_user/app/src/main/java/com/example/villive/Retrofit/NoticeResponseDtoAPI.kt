package com.example.villive.Retrofit

import com.example.villive.model.NoticeResponseDto
import retrofit2.Call
import retrofit2.http.GET

interface NoticeResponseDtoAPI {

    @GET("/notice/")
    fun getAllNoticeResponseDto(): Call<List<NoticeResponseDto>>

}