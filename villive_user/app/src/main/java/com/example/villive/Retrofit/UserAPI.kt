package com.example.villive.Retrofit

import com.example.villive.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserAPI {
    @GET("/user/get-all") // 다 갖고 오기
    fun getAllUsers(): Call<List<User>>

    @POST("/user/save") // 저장하기
    fun save(@Body user: User): Call<User>

    @POST("/member/join") // 회원가입
    fun join(@Body user: User): Call<User>
}