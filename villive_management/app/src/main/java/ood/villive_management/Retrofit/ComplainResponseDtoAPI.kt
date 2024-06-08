package ood.villive_management.Retrofit

import okhttp3.ResponseBody
import ood.villive_management.Model.ComplainResponseDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ComplainResponseDtoAPI {

    @GET("/complain/") // 민원 내용 다 가져오기
    fun getAllComplainResponseDto(): Call<List<ComplainResponseDto>>

    @PUT("/complain/{id}")  // 민원 상태 변경
    fun updateComplain(@Path("id") id: Long, @Query("status") status: String, @Body complainRequestDto: ComplainResponseDto): Call<ResponseBody>

}