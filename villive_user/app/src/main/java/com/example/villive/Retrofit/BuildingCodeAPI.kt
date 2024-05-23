package com.example.villive.Retrofit

import com.example.villive.model.BuildingCode
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface BuildingCodeAPI {
    @POST("/member/addinfo") // 건물코드
    fun addinfo(@Body buildingCode: BuildingCode): Call<BuildingCode>
}