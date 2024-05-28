package com.example.villive.Retrofit

import com.example.villive.model.LogInRequestDto
import com.example.villive.model.LoginResponse
import com.example.villive.model.SignUpRequestDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LogInRequestDtoAPI {
    @POST("/member/login")  // 사용자 로그인
    fun login(@Body logInRequestDto: LogInRequestDto): Call<ResponseBody>

    @GET("/member/name")  // 사용자 이름 가져오기
    fun getMemberName(): Call<ResponseBody>
}