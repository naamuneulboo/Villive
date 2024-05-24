package com.example.villive.Retrofit

import com.example.villive.model.LogInRequestDto
import com.example.villive.model.SignUpRequestDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LogInRequestDtoAPI {

    @POST("/member/login") // 로그인
    fun login(@Body logInRequestDto: LogInRequestDto): Call<Unit>

}