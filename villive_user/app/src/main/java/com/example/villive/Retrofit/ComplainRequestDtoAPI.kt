package com.example.villive.Retrofit

import com.example.villive.model.ComplainRequestDto
import com.example.villive.model.LogInRequestDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ComplainRequestDtoAPI {
    @POST("/complain/add")
    fun add(@Body complainRequestDto: ComplainRequestDto ): Call<ResponseBody>
}