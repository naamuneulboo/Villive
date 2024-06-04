// RetrofitService.kt
package com.example.villive.Retrofit

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitService {
    private const val BASE_URL = "http://113.198.235.222:8080"

    fun getService(context: Context): Retrofit {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                val sharedPreferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
                val token = sharedPreferences.getString("token", null)
                // 로그인 요청이 아닐 때만 토큰을 추가합니다.
                if (!chain.request().url.encodedPath.contains("/member/login")) {
                    if (token != null) {
                        requestBuilder.addHeader("Authorization", "Bearer $token")
                    }
                }

                chain.proceed(requestBuilder.build())
            }

            // 로깅 인터셉터 추가
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}