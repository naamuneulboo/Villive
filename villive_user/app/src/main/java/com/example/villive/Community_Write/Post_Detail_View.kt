package com.example.villive.Community_Write

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
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

class Post_Detail_View : AppCompatActivity() {
    private lateinit var titleTextView: TextView
    private lateinit var contentsTextView: TextView
    private lateinit var writerTextView: TextView
    private lateinit var createDateTextView: TextView
    private lateinit var postId: String // 게시글의 ID를 저장하기 위한 변수
    private lateinit var contentEdit: ImageButton
    private lateinit var contentDelete: ImageButton
    private lateinit var gongGam: Button
    private lateinit var gongGamCount: TextView
    private lateinit var commentAdapter: CommentAdapter
    private lateinit var etAddComment: EditText
    private lateinit var ibtnAddComment: ImageButton
    private lateinit var rvPostsGroup: RecyclerView
    private val commentList = mutableListOf<CommentResponseDto>()

    private var postsLikeCheck = false // 현재 공감 상태를 저장하는 변수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_detail_view)

        titleTextView = findViewById(R.id.post_title)
        contentsTextView = findViewById(R.id.post_contents)
        writerTextView = findViewById(R.id.post_writer)
        createDateTextView = findViewById(R.id.post_create_date)
        contentEdit = findViewById(R.id.ibtn_content_edit)
        contentDelete = findViewById(R.id.ibtn_content_delete)
        gongGam = findViewById(R.id.btn_gong_gam)

        // 뷰 초기화
        etAddComment = findViewById(R.id.et_add_comment)
        ibtnAddComment = findViewById(R.id.ibtn_add_comment)
        rvPostsGroup = findViewById(R.id.rv_posts_group)

        // Intent로부터 게시글의 ID를 가져옴
        postId = intent.getStringExtra("POST_ID").toString()

        // 댓글 목록을 표시할 RecyclerView 설정
        commentAdapter = CommentAdapter(commentList)
        rvPostsGroup.apply {
            adapter = commentAdapter
            layoutManager = LinearLayoutManager(this@Post_Detail_View)
        }

        getPostDetails()

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

                    // contentEdit 버튼 클릭 시 수정 액티비티로 이동
                    contentEdit.setOnClickListener {
                        val intent = Intent(this@Post_Detail_View, Post_Edit_View::class.java).apply {
                            putExtra("POST_ID", postId)
                            putExtra("POST_TITLE", titleTextView.text.toString()) // 게시글 제목 전달
                            putExtra("POST_CONTENTS", contentsTextView.text.toString()) // 게시글 내용 전달
                        }
                        startActivity(intent)
                    }

                    // contentDelete 버튼 클릭 시 삭제 다이얼로그 표시
                    contentDelete.setOnClickListener {
                        showDeleteConfirmationDialog()
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
                    // 댓글 추가에 실패한 경우에 대한 처리
                    // 실패 메시지 등을 사용자에게 표시하거나 필요한 경우 추가적인 작업을 수행합니다.
                }
            }

            override fun onFailure(call: Call<CommentResponseDto>, t: Throwable) {
                // 통신 실패 시 처리
                // 예를 들어, 네트워크 오류 메시지를 사용자에게 표시하거나 다시 시도할 것인지 묻는 등의 작업을 수행할 수 있습니다.
            }
        })
    }
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

                        // 댓글 목록을 가져와서 commentList에 추가
                        it.commentList?.let { comments ->
                            commentList.clear()
                            commentList.addAll(comments)
                            commentAdapter.notifyDataSetChanged()
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


}