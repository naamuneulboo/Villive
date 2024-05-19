package com.example.villive.Community

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.villive.Post_model_adapter.CommentAdapter
import com.example.villive.Post_model_adapter.Comment

import com.example.villive.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class Community_Content : AppCompatActivity() {

    private lateinit var commentAdapter: CommentAdapter
    private val commentList = ArrayList<Comment>()
    private var isGongGamClicked = false // 공감 버튼 클릭 상태를 추적하는 변수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.community_content)

        // Intent로부터 데이터 가져오기
        val title = intent.getStringExtra("title")
        val write = intent.getStringExtra("write")
        val nickname = intent.getStringExtra("nickname")
        val writeTime = intent.getStringExtra("write_time")


        // 뷰에 데이터 설정
        findViewById<TextView>(R.id.tv_content_title).text = title
        findViewById<TextView>(R.id.tv_post_content).text = write
        findViewById<TextView>(R.id.tv_post_nickname).text = nickname
        findViewById<TextView>(R.id.tv_post_time).text = writeTime

        // 댓글 리사이클러뷰 설정
        val rvComments = findViewById<RecyclerView>(R.id.rv_posts_group)
        rvComments.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        commentAdapter = CommentAdapter(commentList)
        rvComments.adapter = commentAdapter



        // 공감 버튼
        val btnGongGam = findViewById<Button>(R.id.btn_gong_gam)
        btnGongGam.setOnClickListener {
            toggleGongGam() // 공감 버튼 클릭 시 상태를 토글하는 함수 호출
        }



        // 댓글 추가
        findViewById<ImageButton>(R.id.ibtn_add_comment).setOnClickListener {
            addComment()
        }


    }

    private fun addComment() {
        val etComment = findViewById<EditText>(R.id.et_add_comment)
        val content = etComment.text.toString()
        if (content.isNotBlank()) {
            val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
            val comment = Comment(content, currentTime)
            commentList.add(comment)
            commentAdapter.notifyItemInserted(commentList.size - 1)
            etComment.text.clear()

            // 댓글 추가 후 해당 위치로 스크롤 (아직 미완)
            val rvComments = findViewById<RecyclerView>(R.id.rv_posts_group)
            rvComments.scrollToPosition(commentList.size - 1)
        }
    }

    private fun toggleGongGam() {
        // 현재 공감 상태에 따라 동작 결정
        if (isGongGamClicked) {
            decreaseGongGamCount()
        } else {
            increaseGongGamCount()
        }

        // 공감 버튼 클릭 상태 반전
        isGongGamClicked = !isGongGamClicked
    }

    private fun increaseGongGamCount() {
        // 현재 공감 수
        val tvGongGamCount = findViewById<TextView>(R.id.tv_gonggam_count)
        val currentCountText = tvGongGamCount.text.toString()
        val currentCount = if (currentCountText.split(" ").isNotEmpty()) currentCountText.split(" ")[0].toInt() else 0

        // 공감 수 증가
        val newCount = currentCount + 1
        tvGongGamCount.text = "$newCount 공감"

        // 공감 수가 0이 아닌 경우 text 표시
        if (newCount > 0) {
            tvGongGamCount.visibility = View.VISIBLE
        }
    }

    private fun decreaseGongGamCount() {
        // 현재 공감 수
        val tvGongGamCount = findViewById<TextView>(R.id.tv_gonggam_count)
        val currentCountText = tvGongGamCount.text.toString()
        val currentCount = if (currentCountText.split(" ").isNotEmpty()) currentCountText.split(" ")[0].toInt() else 0

        // 공감 수 감소
        val newCount = currentCount - 1
        tvGongGamCount.text = "$newCount 공감"

        // 공감 수가 0인 경우 text 숨기기
        if (newCount == 0) {
            tvGongGamCount.visibility = View.INVISIBLE
        }
    }


}