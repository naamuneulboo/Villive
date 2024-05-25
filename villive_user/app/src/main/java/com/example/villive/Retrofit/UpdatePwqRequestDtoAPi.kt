package com.example.villive.Retrofit

import com.example.villive.model.UpdatePwdRequestDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PUT

interface UpdatePwdRequestDtoAPI {

    @PUT("/member/updatePassword")
    fun updatePassword(@Body updatePwdRequestDto: UpdatePwdRequestDto): Call<ResponseBody>

}
