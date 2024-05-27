package com.example.villive.Retrofit


import com.example.villive.model.CarResponseDto
import com.example.villive.model.ComplainResponseDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface  CarResponseDtoAPI {

    @GET("/car/mycars")
    fun getMycar(): Call<List<CarResponseDto>>

    @GET("/car/all")
    fun getAllcars(): Call<List<CarResponseDto>>

}