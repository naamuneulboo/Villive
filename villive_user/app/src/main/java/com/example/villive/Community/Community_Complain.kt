package com.example.villive.Community

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.villive.Community_Write.Post_Complain
import com.example.villive.Post_model_adapter.Complain
import com.example.villive.Post_model_adapter.ComplainAdapter
import com.example.villive.Post_model_adapter.PostAdapter
import com.example.villive.Post_model_adapter.Posts
import com.example.villive.R

class Community_Complain : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.community_complain)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // 일단 임시로 값 넣어뒀음
        val complainlist= arrayListOf(
            Complain("쓰레기좀","아무데나 버리지 마세요","가연","09:03","환경 개선","접수"),
            Complain("화단에","담배꽁초가 너무 많은거같아요","가연","10:01","환경 개선","접수"),
            Complain("공동현관 자동문","고장났어요","가연","10:26","공동 시설","처리 완료"),
            Complain("집 창문에","벌집이 있어요","가연","10:40","기타 민원","처리 완료"),
            Complain("화단에","쓰레기가 많아요","가연","11:07","환경 개선","접수"),
            Complain("엘베","에어컨에서 물떨어져요","가연","11:53","기계 고장","처리 중"),
            Complain("엘베에","전단지 붙여요","가연","12:04","공동 시설","처리 완료"),
            Complain("정원꾸미기","주민들이랑 하면 좋을거같아요 ~ ^^","가연","13:47","환경 개선","접수"),
            Complain("외부인 주차","차단기 설치 해주세요","가연","13:55","건의 사항","처리 중"),

            )

        val rv_complain_post = findViewById<RecyclerView>(R.id.rv_posts_complain)

        rv_complain_post.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_complain_post.setHasFixedSize(true)

// Pass the context to the ComplainAdapter constructor
        rv_complain_post.adapter = ComplainAdapter(complainlist, this)







        val write_post = findViewById<Button>(R.id.btn_write_post)

        write_post.setOnClickListener {
            val intent = Intent(this, Post_Complain::class.java)
            startActivity(intent)
        }
    }
}