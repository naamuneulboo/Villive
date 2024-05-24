package com.example.villive.Retrofit

import com.example.villive.model.LogInRequestDto
import com.example.villive.model.LoginResponse
import com.example.villive.model.SignUpRequestDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LogInRequestDtoAPI {
    @POST("/member/login")
    fun login(@Body logInRequestDto: LogInRequestDto): Call<ResponseBody>
}