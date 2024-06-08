package ood.villive_management.Retrofit


import okhttp3.ResponseBody
import ood.villive_management.Model.ComplainRequestDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ComplainRequestDtoAPI {
    @PUT("/complain/{id}")  // 민원 상태 변경
    fun updateComplain2(@Path("id") id: Long, @Query("status") status: String, @Body complainRequestDto: ComplainRequestDto): Call<ResponseBody>
}
