package ood.villive_management.Retrofit

import okhttp3.ResponseBody
import ood.villive_management.Model.NoticeRequestDto
import ood.villive_management.Model.NoticeResponseDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface NoticeRequestDtoAPI {
    @POST("/notice/add")
    fun write(@Body noticeRequestDto: NoticeRequestDto): Call<ResponseBody>


}