package com.example.villive.Retrofit

import com.example.villive.model.DeleteMemRequestDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE

interface DeleteMemRequestDtoAPI {

    @DELETE("/member/delMember")
    fun deleteMember(@Body deleteMemRequestDto: DeleteMemRequestDto): Call<ResponseBody>

}