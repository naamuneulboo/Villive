package com.example.villive.ConfigurationPage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.villive.R
import com.example.villive.User_SignPage.user_BuildingAuth
import com.example.villive.User_SignPage.user_LogIn

class PasswordChangeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.password_change)
        initializeComponents()
    }

    // 비밀번호 변경 페이지
    fun initializeComponents() {
        val EditPassword = findViewById<TextView>(R.id.textView)
        val inputEditPassword = findViewById<EditText>(R.id.editTextPassword)
        val inputEditNewPassword = findViewById<EditText>(R.id.editTextNewPassword)
        val inputEditCheckPassword = findViewById<EditText>(R.id.editTextCheckPassword)
        val buttonChange = findViewById<Button>(R.id.button)

        // 비밀번호 변경 버튼 클릭하면 로그인화면이동 -> 46번라인 LoginActivity는 로그인화면 클래스로 바꿔주면돼
        buttonChange.setOnClickListener {

            val nowPw = inputEditPassword.text.toString()
            val newPw = inputEditNewPassword.text.toString()
            val pwCheck = inputEditCheckPassword.text.toString()

            if (nowPw.trim().isEmpty() || newPw.trim().isEmpty() || pwCheck.trim().isEmpty()) {
                // 공백이 있을 경우
                Toast.makeText(this, "모든 필드는 공백일 수 없습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 비밀번호 유효성 검사: 영어와 특수문자 포함 8~12자리
            val pwPattern = Regex("(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9])(?=\\S+$).{8,12}")
            if (!pwPattern.matches(newPw)) {
                // 유효하지 않을 경우
                Toast.makeText(this, "비밀번호는 영어와 특수문자를 포함한 8~12자리여야 합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 비밀번호 확인란과 일치하는지 확인
            if (newPw != pwCheck) {
                // 비밀번호 확인이 일치하지 않는 경우
                Toast.makeText(this, "비밀번호와 비밀번호 확인이 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, user_LogIn::class.java);
            startActivity(intent);

            finish()

        }

    }

    /*  // 회원가입 페이지
    private void initializeComponents(){
        EditText inputEditID=findViewById(R.id.editTextText2);
        EditText inputEditPassword=findViewById(R.id.editTextTextPassword);
        EditText inputEditName=findViewById(R.id.editTextText);
        EditText inputEditPhone=findViewById(R.id.editTextTextPassword3);
        EditText inputEditAddress=findViewById(R.id.editTextTextPassword4);


        Button buttonSave=findViewById(R.id.button3);


        RetrofitService retrofitService=new RetrofitService();
        UserAPI userAPI=retrofitService.getRetrofit().create(UserAPI.class);

        buttonSave.setOnClickListener(view -> {
            String id = String.valueOf(inputEditID.getText());
            String password = String.valueOf(inputEditPassword.getText());
            String name = String.valueOf(inputEditName.getText());
            String phone = String.valueOf(inputEditPhone.getText());
            String address = String.valueOf(inputEditAddress.getText());


            User user=new User();
            user.setUser_id(id);
            user.setPassword(password);
            user.setName(name);
            user.setPhone(phone);
            user.setAddress(address);

            userAPI.save(user)
                    .enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) { // 저장이 되었다면
                            Toast.makeText(MainActivity.this, "Save Success", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) { // 저장이 실패했다면
                            Toast.makeText(MainActivity.this, "Save failed", Toast.LENGTH_LONG).show();
                            Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, "Error occurred", t);
                        }
                    });


        });
    }*/
}
