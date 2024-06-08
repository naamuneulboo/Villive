package ood.villive_management.MGMT_main

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ood.villive_management.Adapter.NoticeAdapter
import ood.villive_management.Model.NoticeRequestDto
import ood.villive_management.Model.NoticeResponseDto
import ood.villive_management.R
import ood.villive_management.Retrofit.NoticeResponseDtoAPI
import ood.villive_management.Retrofit.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoticeList : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var noticeAdapter: NoticeAdapter

    companion object {
        const val REQUEST_CODE_ADD_NOTICE = 1
    }

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
                noticeAdapter = NoticeAdapter(noticeResponseDtos.toMutableList())
                recyclerView.adapter = noticeAdapter

                noticeAdapter.setOnDeleteClickListener(object : NoticeAdapter.OnDeleteClickListener {
                    override fun onDeleteClick(post: NoticeResponseDto) {
                        deleteDialog(post.id.toString())
                    }
                })
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
    private fun deleteDialog(noticeId: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("공지사항 삭제")
        builder.setMessage("이 공지사항을 삭제하시겠습니까?")
        builder.setPositiveButton("삭제") { _, _ ->
            deletePost(noticeId)
        }
        builder.setNegativeButton("취소") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun deletePost(noticeId: String) {
        val retrofit = RetrofitService.getService(this)
        val noticeResponseDtoAPI = retrofit.create(NoticeResponseDtoAPI::class.java)

        noticeResponseDtoAPI.delete(noticeId.toLong()).enqueue(object : Callback<NoticeResponseDto> {
            override fun onResponse(call: Call<NoticeResponseDto>, response: Response<NoticeResponseDto>) {
                if (response.isSuccessful) {
                    // 삭제 성공 시 처리
                    val alertDialog = AlertDialog.Builder(this@NoticeList).create()
                    alertDialog.setTitle("삭제 성공")
                    alertDialog.setMessage("공지사항이 성공적으로 삭제되었습니다.")
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "확인") { dialog, _ ->
                        dialog.dismiss()
                        startActivity(intent)
                        finish()
                    }
                    alertDialog.show()
                } else {
                    // 삭제 실패 시 처리
                    val alertDialog = AlertDialog.Builder(this@NoticeList).create()
                    alertDialog.setTitle("삭제 실패")
                    alertDialog.setMessage("공지사항 삭제에 실패했습니다. 다시 시도해주세요.")
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "확인") { dialog, _ ->
                        dialog.dismiss()
                    }
                    alertDialog.show()
                }
            }

            override fun onFailure(call: Call<NoticeResponseDto>, t: Throwable) {
                // 삭제 실패 시 처리
                val alertDialog = AlertDialog.Builder(this@NoticeList).create()
                alertDialog.setTitle("삭제 실패")
                alertDialog.setMessage("공지사항 삭제 중 오류가 발생했습니다. 네트워크 상태를 확인하고 다시 시도해주세요.")
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "확인") { dialog, _ ->
                    dialog.dismiss()
                }
                alertDialog.show()
            }

        })
    }




}