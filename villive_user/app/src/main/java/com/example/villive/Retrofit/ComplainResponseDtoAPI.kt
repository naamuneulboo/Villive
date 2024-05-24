package com.example.villive.Retrofit

import com.example.villive.model.ComplainResponseDto



import retrofit2.Call
import retrofit2.http.GET

interface ComplainResponseDtoAPI {

    @GET("/complain/") // 민원 내용 다 가져오기
    fun getAllComplainResponseDto(): Call<List<ComplainResponseDto>>

}
