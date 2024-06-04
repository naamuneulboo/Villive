package ood.villive_management.Retrofit

import ood.villive_management.Model.LogInRequestDto
import ood.villive_management.Model.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LogInRequestDtoAPI {
    @POST("/member/login")
    fun login(@Body logInRequestDto: LogInRequestDto): Call<LoginResponse>
}