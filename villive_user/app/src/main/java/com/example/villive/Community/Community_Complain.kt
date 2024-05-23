package com.example.villive.Community

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.villive.R
import com.example.villive.Retrofit.ComplainResponseDtoAPI
import com.example.villive.Retrofit.RetrofitService
import com.example.villive.model.ComplainResponseDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Community_Complain : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var complainAdapter: ComplainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.community_complain)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val retrofitService = RetrofitService()
        val complainResponseDtoAPI = retrofitService.retrofit.create(ComplainResponseDtoAPI::class.java)

        val call = complainResponseDtoAPI.getAllComplainResponseDto()
        call.enqueue(object : Callback<List<ComplainResponseDto>> {
            override fun onResponse(call: Call<List<ComplainResponseDto>>, response: Response<List<ComplainResponseDto>>) {
                if (!response.isSuccessful) {
                    // Handle error
                    return
                }

                val complainResponseDtos = response.body() ?: return
                complainAdapter = ComplainAdapter(complainResponseDtos)
                recyclerView.adapter = complainAdapter
            }

            override fun onFailure(call: Call<List<ComplainResponseDto>>, t: Throwable) {
                // Handle failure
            }
        })
    }
}
