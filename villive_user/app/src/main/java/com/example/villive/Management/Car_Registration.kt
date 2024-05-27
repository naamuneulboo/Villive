package com.example.villive.Management

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.villive.R
import com.example.villive.Retrofit.CarRequestDtoAPI
import com.example.villive.Retrofit.CarResponseDtoAPI
import com.example.villive.Retrofit.RetrofitService
import com.example.villive.model.CarRequestDto
import com.example.villive.model.CarResponseDto
import retrofit2.Call
import retrofit2.Callback
import okhttp3.ResponseBody
import retrofit2.Response

class Car_Registration : AppCompatActivity() {

    private lateinit var mycar: String
    private lateinit var carUserId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.car_registration)

        val go_registration = findViewById<Button>(R.id.btn_registration)

        go_registration.setOnClickListener {
            val car_num = findViewById<EditText>(R.id.et_car_num).text.toString()

            if (car_num.isEmpty()) {
                val message = "차량번호는 공백일 수 없습니다."
                AlertDialog.Builder(this)
                    .setMessage(message)
                    .setPositiveButton("확인") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            } else {
                val message = "차량 번호 : $car_num \n 입력하신 정보가 맞습니까?"
                AlertDialog.Builder(this)
                    .setMessage(message)
                    .setPositiveButton("확인") { dialog, _ ->
                        checkCarLimit(car_num)
                        dialog.dismiss()
                    }
                    .setNegativeButton("취소") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }
    }

    private fun checkCarLimit(carNum: String) {
        val retrofit = RetrofitService.getService(this)
        val carResponseDtoAPI = retrofit.create(CarResponseDtoAPI::class.java)

        carResponseDtoAPI.getMycar().enqueue(object : Callback<List<CarResponseDto>> {
            override fun onResponse(call: Call<List<CarResponseDto>>, response: Response<List<CarResponseDto>>) {
                if (response.isSuccessful) {
                    val userCars = response.body() ?: emptyList()
                    if (userCars.size >= 2) {
                        Toast.makeText(this@Car_Registration, "차량은 최대 2대까지 등록할 수 있습니다.", Toast.LENGTH_SHORT).show()
                    } else if (userCars.any { it.carNum == carNum }) {
                        Toast.makeText(this@Car_Registration, "동일한 차량 번호가 이미 등록되어 있습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        registerCar(CarRequestDto(carNum))
                    }
                } else {
                    Log.e("Car_Registration", "차량 정보 조회 실패: ${response.errorBody()?.string()}")
                    Toast.makeText(this@Car_Registration, "차량 정보 조회에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<CarResponseDto>>, t: Throwable) {
                Log.e("Car_Registration", "네트워크 오류: ${t.message}")
                Toast.makeText(this@Car_Registration, "네트워크 오류", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun registerCar(carRequestDto: CarRequestDto) {
        val retrofit = RetrofitService.getService(this)
        val carRequestDtoAPI = retrofit.create(CarRequestDtoAPI::class.java)

        carRequestDtoAPI.addCar(carRequestDto).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@Car_Registration, "차량 등록에 성공했습니다.", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Log.e("Car_Registration", "차량 등록 실패: ${response.errorBody()?.string()}")
                    Toast.makeText(this@Car_Registration, "차량 등록에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("Car_Registration", "네트워크 오류: ${t.message}")
                Toast.makeText(this@Car_Registration, "네트워크 오류", Toast.LENGTH_SHORT).show()
            }
        })
    }
}