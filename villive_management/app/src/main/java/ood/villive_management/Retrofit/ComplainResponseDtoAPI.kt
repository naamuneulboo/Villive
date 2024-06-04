package ood.villive_management.Retrofit

import okhttp3.ResponseBody
import ood.villive_management.Model.ComplainResponseDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ComplainResponseDtoAPI {

    @GET("/complain/") // 민원 내용 다 가져오기
    fun getAllComplainResponseDto(): Call<List<ComplainResponseDto>>

    @PUT("/complain/{id}")  // 민원 상태 변경
    fun updateStatus(@Path("id") id: Long, @Body complainResponseDto: ComplainResponseDto): Call<ResponseBody>
}