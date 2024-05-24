package com.example.villive.Retrofit

import com.example.villive.model.SignUpRequestDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpRequestDtoAPI {
    @POST("/member/join") // 회원가입
    fun join(@Body signUpRequestDto: SignUpRequestDto): Call<Unit>
}
