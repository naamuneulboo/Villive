package com.example.villive.ConfigurationPage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.villive.R
import com.example.villive.Retrofit.MemberDeleteApi
import com.example.villive.Retrofit.RetrofitService
import com.example.villive.User_SignPage.StartPage
import com.example.villive.model.UnregisterRequestDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UnregisterActivity : AppCompatActivity() {

    private lateinit var passwordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.unregister)

        passwordEditText = findViewById(R.id.editTextPassword)
        val unregisterButton = findViewById<Button>(R.id.buttonUnregister)

        unregisterButton.setOnClickListener {
            val password = passwordEditText.text.toString()
            if (password.isNotBlank()) {
                showConfirmationDialog(password)
            } else {
                Toast.makeText(this, "비밀번호를 입력해주세요 !", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showConfirmationDialog(password: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("정말 탈퇴하시겠습니까?")
            .setPositiveButton("네") { dialog, which ->
                unregisterMember(password)
                dialog.dismiss()
            }
            .setNegativeButton("아니오") { dialog, which -> dialog.dismiss() }
        val dialog = builder.create()
        dialog.show()
    }

    private fun unregisterMember(password: String) {
        val retrofit = RetrofitService.getService(this)
        val memberDeleteApi = retrofit.create(MemberDeleteApi::class.java)
        val request = UnregisterRequestDto(password)

        val call = memberDeleteApi.deleteMember(request)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@UnregisterActivity, "회원 탈퇴가 완료되었습니다.\n빌리브를 이용해 주셔서 감사합니다.", Toast.LENGTH_SHORT).show()
                    navigateToStartPage()
                } else {
                    Toast.makeText(this@UnregisterActivity, "회원 탈퇴 실패\n 사유: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@UnregisterActivity, "회원 탈퇴 에러: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun navigateToStartPage() {
        val intent = Intent(this, StartPage::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}
