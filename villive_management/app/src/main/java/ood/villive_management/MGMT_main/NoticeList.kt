package ood.villive_management.MGMT_main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.villive.Retrofit.RetrofitService
import ood.villive_management.Adapter.NoticeAdapter
import ood.villive_management.Model.NoticeResponseDto
import ood.villive_management.R
import ood.villive_management.Retrofit.NoticeResponseDtoAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoticeList : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var noticeAdapter: NoticeAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notice_history)


        recyclerView = findViewById(R.id.rv_posts_notice)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Retrofit 객체 가져오기
        val retrofit = RetrofitService.getService(this)
        val noticeResponseDtoAPI = retrofit.create(NoticeResponseDtoAPI::class.java)

        val call = noticeResponseDtoAPI.getAllNoticeResponseDto()
        call.enqueue(object : Callback<List<NoticeResponseDto>> {
            override fun onResponse(call: Call<List<NoticeResponseDto>>, response: Response<List<NoticeResponseDto>>) {
                if (!response.isSuccessful) {
                    // Handle error
                    return
                }
                val noticeResponseDtos = response.body() ?: return
                noticeAdapter = NoticeAdapter(noticeResponseDtos)
                recyclerView.adapter = noticeAdapter
            }

            override fun onFailure(call: Call<List<NoticeResponseDto>>, t: Throwable) {
                // 데이터 로드 실패 시 처리
                val alertDialog = AlertDialog.Builder(this@NoticeList).create()
                alertDialog.setTitle("로드 실패")
                alertDialog.setMessage("공지사항을 불러오는 중 오류가 발생했습니다. 네트워크 상태를 확인하고 다시 시도해주세요.")
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "확인") { dialog, _ ->
                    dialog.dismiss()
                }
                alertDialog.show()
            }
        })

        val noticePostButton = findViewById<Button>(R.id.btn_notice_post)
        noticePostButton.setOnClickListener {
            val intent = Intent(this, AddNotice::class.java)
            startActivity(intent)
        }
    }
}
