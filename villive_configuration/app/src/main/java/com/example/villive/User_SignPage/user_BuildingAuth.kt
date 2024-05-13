package com.example.villive.User_SignPage

import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.villive.R

class user_BuildingAuth : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.user_building_auth)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toAuth = findViewById<Button>(R.id.btn_to_auth)
        val vill_code = findViewById<EditText>(R.id.et_vill_code)

        toAuth.setOnClickListener {
            val vCode = vill_code.text.toString().trim()

            if (vCode.isEmpty()) {
                // 건물 코드를 입력하지 않은 경우
                showErrorMessage("건물 코드를 입력하세요!")
            } else {
                // 건물 코드 입력 후 확인 누르면 로그인 창으로 이동할 수 있게
                showConfirmationDialog()
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

    /*class NoSpaceFilter : InputFilter {
        override fun filter(
            source: CharSequence?,
            start: Int,
            end: Int,
            dest: Spanned?,
            dstart: Int,
            dend: Int
        ): CharSequence? {
            // 공백 포함 확인
            if (source != null && source.toString().contains(" ")) {
                // 공백 포함 -> 빈 문자열 반환
                return ""
            }
            // 공백 포함 x -> 입력
            return null
        }
    }*/

    }