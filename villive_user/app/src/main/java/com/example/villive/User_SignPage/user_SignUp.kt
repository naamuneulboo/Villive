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
import com.example.villive.Retrofit.RetrofitService
import com.example.villive.Retrofit.UserAPI
import com.example.villive.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.logging.Level
import java.util.logging.Logger

class user_SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.user_sign_up)

        val inputEditID = findViewById<EditText>(R.id.et_id)
        val inputEditPassword = findViewById<EditText>(R.id.et_pw)
        val inputEditName = findViewById<EditText>(R.id.et_name)
        val inputEditNickname = findViewById<EditText>(R.id.et_nickname)
        val inputPasswordCheck = findViewById<EditText>(R.id.et_pw_check)

        val buttonSave = findViewById<Button>(R.id.btn_sign_up)

        // 레트로핏 객체
        val retrofitService = RetrofitService()
        val userAPI = retrofitService.retrofit.create(UserAPI::class.java)

        buttonSave.setOnClickListener {

            val member_id = inputEditID.text.toString()
            val password = inputEditPassword.text.toString()
            val name = inputEditName.text.toString()
            val nickname = inputEditNickname.text.toString()

           val password_check = inputPasswordCheck.text.toString()




            // 공백 및 유효성 검사
            if (member_id.trim().isEmpty() || password.trim().isEmpty() || name.trim().isEmpty() || nickname.trim().isEmpty() || password_check.trim().isEmpty()) {
                Toast.makeText(this, "모든 필드는 공백일 수 없습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val idPattern = Regex("[a-zA-Z0-9]{6,12}")
            if (!idPattern.matches(member_id)) {
                Toast.makeText(this, "아이디는 영어와 숫자를 포함한 6~12자리여야 합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val pwPattern = Regex("(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9])(?=\\S+$).{8,12}")
            if (!pwPattern.matches(password)) {
                Toast.makeText(this, "비밀번호는 영어와 특수문자를 포함한 8~12자리여야 합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != password_check) {
                Toast.makeText(this, "비밀번호와 비밀번호 확인이 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }









            // 모델에 값을 담고
            val user = User().apply {
                this.member_id = member_id
                this.password = password
                this.name = name
                this.nickname = nickname
            }



            // join 엔드포인트에 등록
            userAPI.join(user).enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {  // 잘 들어갔으면 성공 !
                        Toast.makeText(this@user_SignUp, "join Success", Toast.LENGTH_LONG).show()
                        showConfirmationDialog()

                    } else {  // 아니면 실패 ..
                        val errorBody = response.errorBody()?.string()
                        Toast.makeText(this@user_SignUp, "join failed: ${response.code()} - $errorBody", Toast.LENGTH_LONG).show()
                        Logger.getLogger(user_SignUp::class.java.name).log(Level.SEVERE, "Error occurred: ${response.code()} - $errorBody")
                        showConfirmationDialog()  // 일단 디비에는 잘 들어가니 그냥 여기에도  냅뒀음

                    }
                    // 근데 뭔가 이상함
                    // 디비에는 진짜 잘들어가는데 자꾸 네트워크 에러, join failed 보여줌
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(this@user_SignUp, "Network Error", Toast.LENGTH_LONG).show()
                    Logger.getLogger(user_SignUp::class.java.name).log(Level.SEVERE, "Network Error occurred", t)
                }
            })

        }
    }


     private fun showConfirmationDialog() {
         val builder = AlertDialog.Builder(this)
         builder.setMessage("회원가입이 완료되었습니다!\n건물코드 인증 화면으로 이동하시겠습니까?")
             .setPositiveButton("예") { dialog, which ->
                 val intent = Intent(this@user_SignUp, user_BuildingAuth::class.java)
                 startActivity(intent)
                 finish()
             }
             .show()
     }

}
