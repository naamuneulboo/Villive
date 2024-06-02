package com.example.villive.Retrofit

import com.example.villive.model.MsgResponseDto
import com.example.villive.model.UnregisterRequestDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE

interface DeleteMemRequestDtoAPI {

    @DELETE("/member/delMember")
    fun deleteMember(@Body unregisterRequestDto: UnregisterRequestDto): Call<MsgResponseDto>
}