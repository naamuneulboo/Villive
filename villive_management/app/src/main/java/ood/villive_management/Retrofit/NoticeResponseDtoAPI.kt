package ood.villive_management.Retrofit

import ood.villive_management.Model.NoticeResponseDto
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface NoticeResponseDtoAPI {

    @GET("/notice/")
    fun getAllNoticeResponseDto(): Call<List<NoticeResponseDto>>

    @DELETE("/notice/delete/{noticeId}")
    fun delete(@Path("noticeId") noticeId: Long): Call<NoticeResponseDto>
}