package ood.villive_management.MGMT_main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ood.villive_management.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) //xml 화면 뷰 연결

        val notice = findViewById<Button>(R.id.btn_notice)
        val complain = findViewById<Button>(R.id.btn_complain)

        notice.setOnClickListener {
            val intent = Intent(this@MainActivity, NoticeList::class.java) // 다음 화면으로 넘어가기 위한 intent 객체 생성
            startActivity(intent)
        }
        complain.setOnClickListener {
            val intent = Intent(this@MainActivity, ComplainList::class.java) // 다음 화면으로 넘어가기 위한 intent 객체 생성
            startActivity(intent)
        }
    }
}