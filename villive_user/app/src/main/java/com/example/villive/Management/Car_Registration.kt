package com.example.villive.Management

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.villive.R


class Car_Registration  : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.car_registration)

        val go_registration = findViewById<Button>(R.id.btn_registration)

        go_registration.setOnClickListener(){
            val car_num = findViewById<EditText>(R.id.et_car_num).text.toString()

            if(car_num.isEmpty()){
                val message = ("차량번호는 공백일 수 없습니다.")
                AlertDialog.Builder(this)
                    .setMessage(message)
                    .setPositiveButton("확인") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }

            //이미 입력된 정보에 한해 입력 제한 코드 추가 else if

            else{
                val message = ("차량 번호 : $car_num \n 입력하신 정보가 맞습니까?")
                AlertDialog.Builder(this)
                    .setMessage(message)
                    .setPositiveButton("확인") { dialog, _ ->
                        finish()
                    }
                    .setNegativeButton("취소") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
                }

        }



    }
}