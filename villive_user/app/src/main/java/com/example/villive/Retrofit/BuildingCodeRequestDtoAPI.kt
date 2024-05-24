package com.example.villive.Retrofit

import com.example.villive.model.BuildingCodeRequestDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface BuildingCodeRequestDtoAPI {
    @POST("/member/addinfo") // 회원 정보 추가 (건물 코드)
    fun addinfo(@Body buildingCodeRequestDto: BuildingCodeRequestDto): Call<Unit>
}
