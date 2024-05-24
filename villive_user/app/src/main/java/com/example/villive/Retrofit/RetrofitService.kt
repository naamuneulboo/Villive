// RetrofitService.kt
package com.example.villive.Retrofit

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object RetrofitService {
    private val retrofit: Retrofit

    init {
        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .create()

        retrofit = Retrofit.Builder()
            .baseUrl("http://113.198.235.222:8080")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun getService(): Retrofit {
        return retrofit
    }



}