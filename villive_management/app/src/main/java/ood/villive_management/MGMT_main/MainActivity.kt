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

        val tanent = findViewById<Button>(R.id.btn_tanent) // btn_tanent 버튼을 가져옴
        val notice = findViewById<Button>(R.id.btn_notice)
        val complain = findViewById<Button>(R.id.btn_complain)
        val cost = findViewById<Button>(R.id.btn_cost)
        val settings = findViewById<Button>(R.id.btn_settings)


        // 버튼에 클릭 리스너 설정
        tanent.setOnClickListener {
            val intent = Intent(this@MainActivity, Tanent_mgmt::class.java) // 다음 화면으로 넘어가기 위한 intent 객체 생성
            startActivity(intent)
        }
        notice.setOnClickListener {
            val intent = Intent(this@MainActivity, NoticeList::class.java) // 다음 화면으로 넘어가기 위한 intent 객체 생성
            startActivity(intent)
        }
        cost.setOnClickListener {
            val intent = Intent(this@MainActivity, AddUsageHistory::class.java) // 다음 화면으로 넘어가기 위한 intent 객체 생성
            startActivity(intent)
        }
        settings.setOnClickListener {
            val intent = Intent(this@MainActivity, Settings::class.java) // 다음 화면으로 넘어가기 위한 intent 객체 생성
            startActivity(intent)
        }
        complain.setOnClickListener {
            val intent = Intent(this@MainActivity, SolveComplain::class.java) // 다음 화면으로 넘어가기 위한 intent 객체 생성
            startActivity(intent)
        }
    }
}