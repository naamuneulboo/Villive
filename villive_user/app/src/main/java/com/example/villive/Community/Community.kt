package com.example.villive.Community

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.villive.R

class Community : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.community)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }

        val go_group = findViewById<ImageButton>(R.id.ibtn_group)

        go_group.setOnClickListener {
            val intent = Intent(this, Community_Group::class.java)
            startActivity(intent)
        }

        val go_purchase = findViewById<ImageButton>(R.id.ibtn_purchase)

        go_purchase.setOnClickListener {
            val intent = Intent(this, Community_Purchase::class.java)
            startActivity(intent)
        }

        val go_complain = findViewById<ImageButton>(R.id.ibtn_complain)

        go_complain.setOnClickListener {
            val intent = Intent(this, Community_Complain::class.java)
            startActivity(intent)
        }

        val go_share = findViewById<ImageButton>(R.id.ibtn_share)

        go_share.setOnClickListener {
            val intent = Intent(this, Community_Share::class.java)
            startActivity(intent)
        }

        val go_club = findViewById<ImageButton>(R.id.ibtn_club)

        go_club.setOnClickListener {
            val intent = Intent(this, Community_Club::class.java)
            startActivity(intent)
        }

        val notice_tab = findViewById<ImageButton>(R.id.ibtn_notice)

        notice_tab.setOnClickListener {
            val intent = Intent(this, Community_NoticeTab::class.java)
            startActivity(intent)
        }
    }
}