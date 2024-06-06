package com.example.villive.Retrofit


import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Path

interface DeleteCarDtoAPI {
    @DELETE("/car/delete/{carId}")
    fun deleteCar(@Path("carId") carId: Int): Call<ResponseBody>

}
