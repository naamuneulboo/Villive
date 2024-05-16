package com.example.villive.User_SignPage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.villive.R

class StartPage : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.user_start_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val go_signup = findViewById<Button>(R.id.btn_go_signup)

        val go_login = findViewById<Button>(R.id.btn_go_login)

        go_signup.setOnClickListener {
            val intent = Intent(this, user_SignUp::class.java)
            startActivity(intent)
        }

        go_login.setOnClickListener {
            val intent = Intent(this, user_LogIn::class.java)
            startActivity(intent)
        }
    }
}