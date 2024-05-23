package com.example.villive.User_SignPage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.villive.R
import com.example.villive.Retrofit.BuildingCodeAPI
import com.example.villive.Retrofit.RetrofitService
import com.example.villive.model.BuildingCode
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.logging.Level
import java.util.logging.Logger

class user_BuildingAuth : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.user_building_auth)


        val inputEditID = findViewById<EditText>(R.id.et_vill_code)
        val inputEditPassword = findViewById<EditText>(R.id.et_vill_num)

        val buttonSave = findViewById<Button>(R.id.btn_to_auth)

        // 레트로핏 객체
        val retrofitService = RetrofitService()
        val buildingCodeAPI = retrofitService.retrofit.create(BuildingCodeAPI::class.java)

        buttonSave.setOnClickListener {
            val address = inputEditID.text.toString().trim()
            val building_code = inputEditPassword.text.toString().trim()

            if (address.isEmpty()&&building_code.isEmpty()) {
                // 건물 코드를 입력하지 않은 경우
                showErrorMessage("모든 필드를 입력해주세요!")
            } else if(address.isEmpty()){
                showErrorMessage("세대 호수를 입력하세요!")
            }else if(building_code.isEmpty()){
                showErrorMessage("건물 코드를 입력하세요!")
            }else{

                val buildingCode = BuildingCode().apply {
                    this.address = address
                    this.building_code = building_code
                }

                buildingCodeAPI.addinfo(buildingCode).enqueue(object : Callback<BuildingCode> {

                    override fun onResponse(call: Call<BuildingCode>, response: Response<BuildingCode>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@user_BuildingAuth, "Building Auth Success", Toast.LENGTH_LONG).show()

                            // 건물 코드 입력 후 확인 누르면 로그인 창으로 이동할 수 있게
                            showConfirmationDialog()
                        } else {
                            val errorBody = response.errorBody()?.string()
                            Toast.makeText(this@user_BuildingAuth, "Building Auth failed: ${response.code()} - $errorBody", Toast.LENGTH_LONG).show()
                            Logger.getLogger(user_BuildingAuth::class.java.name)
                                .log(Level.SEVERE, "Error occurred: ${response.code()} - $errorBody")

                        }
                    }

                    override fun onFailure(call: Call<BuildingCode>, t: Throwable) {
                        Toast.makeText(this@user_BuildingAuth, "Network Error", Toast.LENGTH_LONG)
                            .show()
                        Logger.getLogger(user_BuildingAuth::class.java.name)
                            .log(Level.SEVERE, "Network Error occurred", t)
                    }
                })

            }









        }

    }

    private fun showErrorMessage(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message)
            .setPositiveButton("확인", null)
            .show()
    }


    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("건물 인증이 완료되었습니다!\n로그인 화면으로 이동하시겠습니까?")
            .setPositiveButton("예") { dialog, _ ->
                // 예 -> 회원가입 창으로 이동
                val intent = Intent(this@user_BuildingAuth, user_LogIn::class.java)
                startActivity(intent)
                // 이동 시 건물 인증 창은 필요 x
                finish()
            }
            .show()
    }

}