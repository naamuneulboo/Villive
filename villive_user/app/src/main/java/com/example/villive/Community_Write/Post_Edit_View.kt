package com.example.villive.Community_Write

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.villive.Community.Community
import com.example.villive.R
import com.example.villive.Retrofit.PostsRequestDtoAPI
import com.example.villive.Retrofit.RetrofitService
import com.example.villive.model.PostsRequestDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Post_Edit_View : AppCompatActivity() {
    private lateinit var editTitleEditText: EditText
    private lateinit var editContentEditText: EditText
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_edit_view)

        // 이전 화면에서 전달된 게시글 제목과 내용을 받아옴
        val postTitle = intent.getStringExtra("POST_TITLE")
        val postContents = intent.getStringExtra("POST_CONTENTS")


        editTitleEditText = findViewById(R.id.et_edit_title)
        editContentEditText = findViewById(R.id.et_edit_write)
        btnSave = findViewById(R.id.btn_edit_save)

        // 받아온 게시글 제목과 내용을 EditText에 설정
        editTitleEditText.setText(postTitle)
        editContentEditText.setText(postContents)


        btnSave.setOnClickListener {
            val editedTitle = editTitleEditText.text.toString()
            val editedContent = editContentEditText.text.toString()

            val editedPostDto = PostsRequestDto(title = editedTitle, contents = editedContent)

            val postId = intent.getStringExtra("POST_ID")?.toLongOrNull()

            postId?.let {
                val retrofit = RetrofitService.getService(this)
                val postsRequestDtoAPI = retrofit.create(PostsRequestDtoAPI::class.java)

                postsRequestDtoAPI.updatePost(it, editedPostDto).enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            // 업데이트 성공 팝업 표시
                            showSuccessDialog()
                        } else {
                            // 업데이트 실패 팝업 표시
                            showFailureDialog()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        // 네트워크 오류 등의 실패 처리
                        showFailureDialog()
                    }
                })
            }
        }
    }

    private fun showSuccessDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("게시글이 성공적으로 수정되었습니다.")
            .setPositiveButton("예") { dialog, which ->
                // Community 화면으로 이동
                val intent = Intent(this, Community::class.java)
                startActivity(intent)
                finish()
            }
            .show()
    }

    private fun showFailureDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("게시글 수정에 실패했습니다.")
            .setPositiveButton("확인", null)
            .show()
    }
}