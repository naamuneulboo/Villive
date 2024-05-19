package com.example.villive.Community

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.villive.Post_model_adapter.Complain
import com.example.villive.Post_model_adapter.ComplainAdapter
import com.example.villive.R

class Complain_status : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.community_complain_status)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val complainlist = arrayListOf(
            Complain("쓰레기좀", "아무데나 버리지 마세요", "가연", "09:03", "환경 개선"),
            Complain("화단에", "담배꽁초가 너무 많은거같아요", "가연", "10:01", "환경 개선"),
            Complain("공동현관 자동문", "고장났어요", "가연", "10:26", "공동 시설"),
            Complain("집 창문에", "벌집이 있어요", "가연", "10:40", "기타 민원"),
            Complain("화단에", "쓰레기가 많아요", "가연", "11:07", "환경 개선"),
            Complain("엘베", "에어컨에서 물떨어져요", "가연", "11:53", "기계 고장"),
            Complain("엘베에", "전단지 붙여요", "가연", "12:04", "공동 시설"),
            Complain("정원꾸미기", "주민들이랑 하면 좋을거같아요 ~ ^^", "가연", "13:47", "환경 개선"),
            Complain("외부인 주차", "차단기 설치 해주세요", "가연", "13:55", "건의 사항")
        )

        val rvTitlesAuthors = findViewById<RecyclerView>(R.id.rv_complain_status)
        rvTitlesAuthors.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvTitlesAuthors.setHasFixedSize(true)
        rvTitlesAuthors.adapter = ComplainAdapter(complainlist)

        // 민원 게시판이랑 똑같은 내용을 불러오는데 리사이클러 뷰에는 제목과 내용만 표시 + 여기에 처리 완료 여부 텍스트로
        rvTitlesAuthors.post {
            for (i in 0 until complainlist.size) {
                val itemView = rvTitlesAuthors.layoutManager?.findViewByPosition(i)
                itemView?.apply {
                    findViewById<TextView>(R.id.tv_nickname)?.visibility = View.GONE
                    findViewById<TextView>(R.id.tv_write_time)?.visibility = View.GONE
                    findViewById<TextView>(R.id.tv_complain_kind)?.visibility = View.GONE
                }
            }
        }



    }
}
