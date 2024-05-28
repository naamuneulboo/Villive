package com.example.villive.User_SignPage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.villive.MainActivity
import com.example.villive.R
import com.example.villive.model.BuildingCodeRequestDto
import com.example.villive.Retrofit.BuildingCodeRequestDtoAPI
import com.example.villive.Retrofit.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class user_BuildingAuth : AppCompatActivity() {
    private lateinit var retrofitService: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_building_auth)

        val villCode = findViewById<EditText>(R.id.et_vill_code)
        val villNum = findViewById<EditText>(R.id.et_vill_num)
        val goAuth = findViewById<Button>(R.id.btn_to_auth)

        // 스페이스 입력 비활
        val noSpaceFilter = InputFilter { source, start, end, dest, dstart, dend ->
            for (i in start until end) {
                if (Character.isWhitespace(source[i])) {
                    return@InputFilter ""
                }
            }
            null
        }

        // EditText에 스페이스 비활 적용
        villCode.filters = arrayOf(noSpaceFilter)
        villNum.filters = arrayOf(noSpaceFilter)

        // 숫자만 입력 가능
        val numberFilter = InputFilter { source, start, end, dest, dstart, dend ->
            for (i in start until end) {
                if (!Character.isDigit(source[i])) {
                    return@InputFilter ""
                }
            }
            null
        }

        // EditText에 적용
        villCode.filters = arrayOf(numberFilter)
        villNum.filters = arrayOf(numberFilter)

        // Retrofit 객체 생성
        retrofitService = RetrofitService.getService(this)
        val buildingCodeRequestDtoAPI = retrofitService.create(BuildingCodeRequestDtoAPI::class.java)

        goAuth.setOnClickListener {
            val address = villCode.text.toString()
            val building_code = villNum.text.toString()

            // 건물인증 요청 데이터 생성
            val buildingCodeRequestDto = BuildingCodeRequestDto(address, building_code)

            // 건물인증 요청 API 호출
            buildingCodeRequestDtoAPI.addinfo(buildingCodeRequestDto).enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful) {
                        showConfirmationDialog()
                    } else {
                        Log.e("BuildingAuth", "Error: ${response.errorBody()?.string()}")
                        showErrorDialog()
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    Log.e("BuildingAuth", "Network error", t)
                    showNetworkErrorDialog()
                }
            })
        }
    }

    private fun showConfirmationDialog() {
        AlertDialog.Builder(this)
            .setMessage("건물 인증이 완료되었습니다. 메인 화면으로 이동합니다.")
            .setPositiveButton("네") { _, _ ->
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .setNegativeButton("아니오") { _, _ ->
                startActivity(Intent(this, StartPage::class.java))
                finish()
            }
            .show()
    }

    private fun showErrorDialog() {
        AlertDialog.Builder(this)
            .setMessage("건물 인증에 실패했습니다.")
            .setPositiveButton("확인") { _, _ -> }
            .show()
    }

    private fun showNetworkErrorDialog() {
        AlertDialog.Builder(this)
            .setMessage("네트워크 오류가 발생했습니다.")
            .setPositiveButton("확인") { _, _ -> }
            .show()
    }
}
