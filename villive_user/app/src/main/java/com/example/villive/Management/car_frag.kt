package com.example.villive.Management

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.villive.R
import com.example.villive.Retrofit.CarResponseDtoAPI
import com.example.villive.Retrofit.RetrofitService
import com.example.villive.model.CarResponseDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class car_frag : Fragment() {

    private lateinit var carAdapter: CarAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_car_frag, container, false)

        val car_regi = view.findViewById<Button>(R.id.btn_go_registration)
        recyclerView = view.findViewById(R.id.car_recycler)

        carAdapter = CarAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = carAdapter

        car_regi.setOnClickListener {
            val intent = Intent(activity, Car_Registration::class.java)
            startActivity(intent)
        }

        fetchUserCars()

        return view
    }

    private fun fetchUserCars() {
        val retrofit = RetrofitService.getService(requireContext())
        val carResponseDtoAPI = retrofit.create(CarResponseDtoAPI::class.java)

        carResponseDtoAPI.getMycar().enqueue(object : Callback<List<CarResponseDto>> {
            override fun onResponse(call: Call<List<CarResponseDto>>, response: Response<List<CarResponseDto>>) {
                if (response.isSuccessful) {
                    val userCars = response.body() ?: emptyList()
                    carAdapter.updateCars(userCars)
                } else {
                    Toast.makeText(requireContext(), "차량 정보 조회에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<CarResponseDto>>, t: Throwable) {
                Toast.makeText(requireContext(), "네트워크 오류", Toast.LENGTH_SHORT).show()
            }
        })
    }
}