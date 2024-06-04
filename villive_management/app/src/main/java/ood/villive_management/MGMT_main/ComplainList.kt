package ood.villive_management.MGMT_main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.villive.Community.ComplainAdapter
import com.example.villive.Retrofit.RetrofitService
import ood.villive_management.Model.ComplainResponseDto
import ood.villive_management.R
import ood.villive_management.Retrofit.ComplainResponseDtoAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ComplainList : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var complainAdapter: ComplainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.community_complain)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Retrofit 객체 가져오기
        val retrofit = RetrofitService.getService(this)
        val complainResponseDtoAPI = retrofit.create(ComplainResponseDtoAPI::class.java)


        val call = complainResponseDtoAPI.getAllComplainResponseDto()
        call.enqueue(object : Callback<List<ComplainResponseDto>> {
            override fun onResponse(call: Call<List<ComplainResponseDto>>, response: Response<List<ComplainResponseDto>>) {
                if (!response.isSuccessful) {
                    // Handle error
                    return
                }

                val complainResponseDtos = response.body() ?: return
                complainAdapter = ComplainAdapter(this@ComplainList, complainResponseDtos.toMutableList())
                recyclerView.adapter = complainAdapter

                complainAdapter.setOnUpdateStatusClickListener(object : ComplainAdapter.OnUpdateStatusClickListener {
                    override fun onUpdateClick(complain: ComplainResponseDto, newStatus: ComplainResponseDto.Status) {
                        //val complainId = complain.id.toString() // 민원의 id 가져오기
                        //complainAdapter.updateStatus(complainId, newStatus)

                    }
                })
            }

            override fun onFailure(call: Call<List<ComplainResponseDto>>, t: Throwable) {
                // Handle failure
            }
        })
    }


}