package com.example.villive.Community_Write

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.villive.Post_model_adapter.CommentAdapter
import com.example.villive.R
import com.example.villive.Retrofit.MsgResponseDtoAPI
import com.example.villive.Retrofit.PostsResponseDtoAPI
import com.example.villive.Retrofit.RetrofitService
import com.example.villive.model.CommentRequestDto
import com.example.villive.model.CommentResponseDto
import com.example.villive.model.PostsResponseDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Post_Detail_View : AppCompatActivity(), CommentAdapter.OnItemDeleteClickListener {

    // 게시글 내용
    private lateinit var titleTextView: TextView
    private lateinit var contentsTextView: TextView
    private lateinit var writerTextView: TextView
    private lateinit var createDateTextView: TextView

    // 게시글의 ID 저장
    private lateinit var postId: String

    // 공감
    private lateinit var gongGam: Button
    private lateinit var gongGamCount: TextView
    private lateinit var likeEmoji: ImageView
    private var postsLikeCheck = false

    // 댓글
    private lateinit var commentAdapter: CommentAdapter
    private lateinit var etAddComment: EditText
    private lateinit var ibtnAddComment: ImageButton
    private lateinit var rvComments: RecyclerView
    private val commentList = mutableListOf<CommentResponseDto>()

    // 게시글 수정 / 삭제 메뉴
    private lateinit var ibtn_content_menu:ImageButton


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_detail_view)

        titleTextView = findViewById(R.id.post_title)
        contentsTextView = findViewById(R.id.post_contents)
        writerTextView = findViewById(R.id.post_writer)
        createDateTextView = findViewById(R.id.post_create_date)
        gongGamCount = findViewById(R.id.tv_gonggam_count)

        likeEmoji = findViewById(R.id.like_emoji)

        gongGam = findViewById(R.id.btn_gong_gam)
        ibtn_content_menu =findViewById(R.id.ibtn_content_menu)

        // 뷰 초기화
        etAddComment = findViewById(R.id.et_add_comment)
        ibtnAddComment = findViewById(R.id.ibtn_add_comment)
        rvComments = findViewById(R.id.rv_comments)

        // Intent로부터 게시글의 ID를 가져옴
        postId = intent.getStringExtra("POST_ID").toString()

        // 댓글 목록을 표시할 RecyclerView 설정
        commentAdapter = CommentAdapter(commentList)
        // 액티비티가 삭제 이벤트를 처리할 수 있도록 리스너 설정
        commentAdapter.setOnDeleteClickListener(this)
        rvComments.apply {
            adapter = commentAdapter
            layoutManager = LinearLayoutManager(this@Post_Detail_View)
        }

        getPostDetails()

        // 공감 버튼에 클릭 리스너 추가
        gongGam.setOnClickListener {
            if (postsLikeCheck) {
                // 이미 공감한 경우
                // ImageView를 보이도록 설정
                likeEmoji.visibility = View.VISIBLE
                // 공감 취소 요청 보내기
                likePost()
            } else {
                // 공감하지 않은 경우
                // ImageView를 숨기도록 설정
                likeEmoji.visibility = View.INVISIBLE
                // 게시글 공감 요청 보내기
                likePost()
            }
        }


        // 게시글의 ID를 사용하여 서버에서 해당하는 게시글의 상세 정보를 가져옴
        val retrofit = RetrofitService.getService(this)
        val postsResponseDtoAPI = retrofit.create(PostsResponseDtoAPI::class.java)

        postsResponseDtoAPI.getPostById(postId.toLong()).enqueue(object : Callback<PostsResponseDto> {
            override fun onResponse(call: Call<PostsResponseDto>, response: Response<PostsResponseDto>) {
                if (response.isSuccessful) {
                    val post = response.body()
                    // 가져온 데이터를 각각의 TextView에 설정
                    titleTextView.text = post?.title
                    contentsTextView.text = post?.contents
                    writerTextView.text = post?.writer
                    createDateTextView.text = post?.createDate

                    // ibtn_content_menu 클릭 이벤트 처리
                    ibtn_content_menu.setOnClickListener {
                        val options = arrayOf("수정", "삭제")
                        AlertDialog.Builder(this@Post_Detail_View)
                            .setTitle("게시글 수정 또는 삭제")
                            .setItems(options) { _, which ->
                                when (which) {
                                    0 -> {
                                        // 수정을 선택한 경우
                                        val intent = Intent(this@Post_Detail_View, Post_Edit_View::class.java).apply {
                                            putExtra("POST_ID", postId)
                                            putExtra("POST_TITLE", titleTextView.text.toString()) // 게시글 제목 전달
                                            putExtra("POST_CONTENTS", contentsTextView.text.toString()) // 게시글 내용 전달
                                        }
                                        startActivity(intent)
                                    }
                                    1 -> {
                                        // 삭제를 선택한 경우
                                        showDeleteConfirmationDialog()
                                    }
                                }
                            }
                            .setNegativeButton("취소") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .show()
                    }



                    // 댓글 추가 버튼 클릭 시 동작 설정
                    ibtnAddComment.setOnClickListener {
                        val commentContent = etAddComment.text.toString().trim()
                        if (commentContent.isNotEmpty()) {
                            addComment(commentContent)
                        }
                    }

                } else {
                    // 실패 시 처리
                }
            }

            override fun onFailure(call: Call<PostsResponseDto>, t: Throwable) {
                // 실패 시 처리
            }
        })


    }

    // 게시글 공감 요청 보내기
    private fun likePost() {
        val retrofit = RetrofitService.getService(this)
        val msgResponseDtoAPI = retrofit.create(MsgResponseDtoAPI::class.java)

        msgResponseDtoAPI.likePost(postId.toLong()).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    // 공감 요청 성공 시 처리
                    Toast.makeText(this@Post_Detail_View, "게시글에 공감했습니다!", Toast.LENGTH_SHORT).show()
                    // 공감이 성공하면 게시글을 다시 불러옴
                    getPostDetails()
                } else {
                    // 실패 시 처리
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // 실패 시 처리
            }
        })
    }

    // 게시글 삭제 확인
    private fun showDeleteConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("게시글 삭제")
        builder.setMessage("정말로 이 게시글을 삭제하시겠습니까?")
        builder.setPositiveButton("삭제") { _, _ ->
            deletePost()
        }
        builder.setNegativeButton("취소") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    // 게시글 삭제 요청 수행
    private fun deletePost() {
        val retrofit = RetrofitService.getService(this)
        val msgResponseDtoAPI = retrofit.create(MsgResponseDtoAPI::class.java)

        msgResponseDtoAPI.deletePost(postId.toLong()).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    // 삭제 성공 시 처리
                    val alertDialog = AlertDialog.Builder(this@Post_Detail_View).create()
                    alertDialog.setTitle("삭제 성공")
                    alertDialog.setMessage("게시글이 성공적으로 삭제되었습니다.")
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "확인") { dialog, _ ->
                        dialog.dismiss()
                        // 게시판 화면으로 이동
                        navigateToBoard()
                    }
                    alertDialog.show()
                } else {
                    // 삭제 실패 시 처리
                    val alertDialog = AlertDialog.Builder(this@Post_Detail_View).create()
                    alertDialog.setTitle("삭제 실패")
                    alertDialog.setMessage("게시글 삭제에 실패했습니다. 다시 시도해주세요.")
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "확인") { dialog, _ ->
                        dialog.dismiss()
                    }
                    alertDialog.show()
                }
            }

            override fun onFailure(call: Call<
                    ResponseBody>, t: Throwable) {
                // 삭제 실패 시 처리
                val alertDialog = AlertDialog.Builder(this@Post_Detail_View).create()
                alertDialog.setTitle("삭제 실패")
                alertDialog.setMessage("게시글 삭제 중 오류가 발생했습니다. 네트워크 상태를 확인하고 다시 시도해주세요.")
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "확인") { dialog, _ ->
                    dialog.dismiss()
                }
                alertDialog.show()
            }
        })
    }

    private fun navigateToBoard() {
        finish()
    }

    // 댓글 등록 요청 수행
    private fun addComment(content: String) {
        val retrofit = RetrofitService.getService(this)
        val msgResponseDtoAPI = retrofit.create(MsgResponseDtoAPI::class.java)

        // 댓글 추가 요청 보내기
        msgResponseDtoAPI.addComment(postId.toLong(), CommentRequestDto(content)).enqueue(object : Callback<CommentResponseDto> {
            override fun onResponse(call: Call<CommentResponseDto>, response: Response<CommentResponseDto>) {
                if (response.isSuccessful) {
                    // 성공적으로 댓글이 추가된 경우 RecyclerView에 추가
                    val addedComment = response.body()
                    addedComment?.let {

                        commentList.add(it)
                        commentAdapter.notifyDataSetChanged()

                        // EditText 비우기
                        etAddComment.text.clear()
                    }
                } else {
                    // 댓글 추가 실패 시
                }
            }

            override fun onFailure(call: Call<CommentResponseDto>, t: Throwable) {
                // 실패 시 처리
            }
        })
    }

    // 게시글 상세 정보
    private fun getPostDetails() {
        val retrofit = RetrofitService.getService(this)
        val postsResponseDtoAPI = retrofit.create(PostsResponseDtoAPI::class.java)

        // 게시글 상세 정보 요청
        postsResponseDtoAPI.getPostById(postId.toLong()).enqueue(object : Callback<PostsResponseDto> {
            override fun onResponse(call: Call<PostsResponseDto>, response: Response<PostsResponseDto>) {
                if (response.isSuccessful) {
                    val post = response.body()
                    post?.let {
                        // 게시글 데이터를 화면에 표시
                        titleTextView.text = it.title
                        contentsTextView.text = it.contents
                        writerTextView.text = it.writer
                        createDateTextView.text = it.createDate
                        postsLikeCheck = it.postsLikeCheck ?: false
                        gongGamCount.text = it.postsLikeCnt.toString()

                        // 댓글 목록을 가져와서 commentList에 추가
                        it.commentList?.let { comments ->
                            commentList.clear()
                            commentList.addAll(comments)
                            commentAdapter.notifyDataSetChanged()
                        }

                        // postsLikeCheck 상태에 따라서 like_emoji ImageView의 가시성 설정
                        if (postsLikeCheck) {
                            // 이미 공감한 경우
                            likeEmoji.visibility = View.VISIBLE
                        } else {
                            // 공감하지 않은 경우
                            likeEmoji.visibility = View.INVISIBLE
                        }
                    }
                } else {
                    // 실패 시 처리
                }
            }

            override fun onFailure(call: Call<PostsResponseDto>, t: Throwable) {
                // 실패 시 처리
            }
        })
    }

    // 댓글 삭제 확인
    private fun deleteCommentWithConfirmation(commentId: Long) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("댓글 삭제")
        builder.setMessage("정말로 이 댓글을 삭제하시겠습니까?")
        builder.setPositiveButton("삭제") { _, _ ->
            // 댓글 삭제 요청 처리
            deleteComment(commentId)
        }
        builder.setNegativeButton("취소") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }


    // 댓글 삭제 수행
    private fun deleteComment(commentId: Long) {
        val retrofit = RetrofitService.getService(this)
        val msgResponseDtoAPI = retrofit.create(MsgResponseDtoAPI::class.java)

        msgResponseDtoAPI.deleteComment(postId.toLong(), commentId).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    // 삭제 성공
                    val alertDialog = AlertDialog.Builder(this@Post_Detail_View).create()
                    alertDialog.setTitle("삭제 성공")
                    alertDialog.setMessage("댓글이 성공적으로 삭제되었습니다.")
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "확인") { dialog, _ ->
                        dialog.dismiss()
                        // 댓글 목록 갱신
                        getPostDetails()
                    }
                    alertDialog.show()
                } else {
                    // 삭제 실패
                    val alertDialog = AlertDialog.Builder(this@Post_Detail_View).create()
                    alertDialog.setTitle("삭제 실패")
                    alertDialog.setMessage("댓글 삭제에 실패했습니다. 다시 시도해주세요.")
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "확인") { dialog, _ ->
                        dialog.dismiss()
                        // 댓글 목록 갱신
                        getPostDetails()
                    }
                    alertDialog.show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // 삭제 실패
                val alertDialog = AlertDialog.Builder(this@Post_Detail_View).create()
                alertDialog.setTitle("삭제 실패")
                alertDialog.setMessage("댓글 삭제 중 오류가 발생했습니다. 네트워크 상태를 확인하고 다시 시도해주세요.")
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "확인") { dialog, _ ->
                    dialog.dismiss()
                }
                alertDialog.show()
            }
        })
    }

    // 댓글 삭제 리스너
    override fun onDeleteClick(commentId: Long) {
        // 댓글 삭제 요청
        deleteCommentWithConfirmation(commentId)
    }


}