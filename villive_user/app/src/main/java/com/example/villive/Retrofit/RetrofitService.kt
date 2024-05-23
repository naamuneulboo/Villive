package com.example.villive.Retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {
    val retrofit: Retrofit

    init {
        retrofit = initializeRetrofit()
    }

    private fun initializeRetrofit(): Retrofit {
        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") // 서버에서 사용하는 날짜 포맷에 맞춤
            .create()

        return Retrofit.Builder()
            .baseUrl("http://113.198.235.222:8080")
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
    }


}