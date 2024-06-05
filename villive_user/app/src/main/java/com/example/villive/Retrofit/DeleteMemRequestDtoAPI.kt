package com.example.villive.Retrofit

import com.example.villive.model.UnregisterRequestDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.HTTP

interface MemberDeleteApi {
    @HTTP(method = "DELETE", path = "/member/delMember", hasBody = true)  // delete에 body를 추가 해야할 경우 이렇게 !! 원래 delete에는 body가 못들어감
    fun deleteMember(@Body request: UnregisterRequestDto): Call<ResponseBody>
}
