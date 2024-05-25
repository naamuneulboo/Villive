package com.example.villive.Retrofit

import retrofit2.Call
import okhttp3.ResponseBody
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface MsgResponseDtoAPI {

    @DELETE("/posts/delete/{id}")  // 해당 id 게시글 삭제
    fun deletePost(@Path("id") postId: Long): Call<ResponseBody>

    @POST("/posts/like/{id}")  // 게시글 좋아요
    fun likePost(@Path("id") postId: Long): Call<ResponseBody>
}
