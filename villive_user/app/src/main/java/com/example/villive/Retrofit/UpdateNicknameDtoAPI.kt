package com.example.villive.Retrofit

import com.example.villive.model.UpdateNicknameDto
import okhttp3.Response
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import okhttp3.ResponseBody as ResponseBody1

interface UpdateNicknameDtoAPI {

    @PUT("/member/updateNickname")  // 닉네임 수정
    fun updateNickname(@Body updateNicknameDto: UpdateNicknameDto): Call<ResponseBody1>

    @GET("/member/isExist/{nickname}")
    fun checkNicknameExistence(@Path("nickname") nickname: String): Call<ResponseBody1>
}
