package com.example.villive.Retrofit

import com.example.villive.model.CarRequestDto
import com.example.villive.model.ComplainRequestDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface  CarRequestDtoAPI {

    @POST("/car/add")
    fun addCar(@Body CarRequestDto: CarRequestDto): Call<ResponseBody>

}
