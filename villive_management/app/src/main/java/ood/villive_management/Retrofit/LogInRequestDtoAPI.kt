package ood.villive_management.Retrofit

import okhttp3.ResponseBody
import ood.villive_management.Model.LogInRequestDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LogInRequestDtoAPI {
    @POST("/member/login")
    fun login(@Body logInRequestDto: LogInRequestDto): Call<ResponseBody>
}