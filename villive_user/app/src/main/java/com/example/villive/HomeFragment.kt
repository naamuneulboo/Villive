package com.example.villive

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.villive.Building_Issue.Building_Issue
import com.example.villive.Building_Issue.IssueAdapter
import com.example.villive.Community.Community
import com.example.villive.Community.Community_Complain
import com.example.villive.Community_Write.Post_Complain
import com.example.villive.Community_Write.Post_Detail_View
import com.example.villive.Notice.NoticeList
import com.example.villive.Retrofit.NoticeResponseDtoAPI
import com.example.villive.Retrofit.PostsResponseDtoAPI
import com.example.villive.Retrofit.RetrofitService
import com.example.villive.model.NoticeResponseDto
import com.example.villive.model.PostsResponseDto
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private val eventList = arrayListOf("캔/병", "일반", "음식물", "종이","플라스틱")

    private lateinit var recyclerView: RecyclerView
    private lateinit var issueAdapter: IssueAdapter
    private lateinit var postsResponseDtoAPI: PostsResponseDtoAPI

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fetchPostsData()
        fetchNoticeData()


        val view = inflater.inflate(R.layout.home, container, false)


        recyclerView = view.findViewById(R.id.rv_posts_club)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        val retrofit = RetrofitService.getService(requireContext())
        postsResponseDtoAPI = retrofit.create(PostsResponseDtoAPI::class.java)

        getAllPosts()


        val calendar = Calendar.getInstance()

        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)

        fun getCurrentDateTime(): String {
            val sdf = SimpleDateFormat("yyyy년 MM월 dd일 ", Locale.getDefault())
            return sdf.format(Date())
        }

        val dayOfWeekString = when (dayOfWeek) {
            Calendar.SUNDAY -> "일요일"
            Calendar.MONDAY -> "월요일"
            Calendar.TUESDAY -> "화요일"
            Calendar.WEDNESDAY -> "수요일"
            Calendar.THURSDAY -> "목요일"
            Calendar.FRIDAY -> "금요일"
            Calendar.SATURDAY -> "토요일"
            else -> "알 수 없음"
        }

        val currentDateInfo: String = getCurrentDateTime() + dayOfWeekString


        val eventText = dayOfYear%eventList.size

        view.findViewById<TextView>(R.id.date_tv).text = currentDateInfo
        view.findViewById<TextView>(R.id.event_tv).text = eventList[eventText] + " 배출일"

        val noticeLayout = view.findViewById<LinearLayout>(R.id.notice_lo)

        noticeLayout.setOnClickListener {
            val intent = Intent(activity, NoticeList::class.java)
            startActivity(intent)
        }

        val issueLayout = view.findViewById<FrameLayout>(R.id.go_issue)

        issueLayout.setOnClickListener {
            val intent = Intent(activity, Building_Issue::class.java)
            startActivity(intent)
        }

        val boardLayout = view.findViewById<LinearLayout>(R.id.board_lo)

        boardLayout.setOnClickListener {
            val intent = Intent(activity, Community::class.java)
            startActivity(intent)
        }


        val quick_machine = view.findViewById<Button>(R.id.btn_quick_machine)
        val quick_public = view.findViewById<Button>(R.id.btn_quick_public)
        val quick_env = view.findViewById<Button>(R.id.btn_quick_env)
        val quick_talk = view.findViewById<Button>(R.id.btn_quick_talk)
        val quick_etc = view.findViewById<Button>(R.id.btn_quick_etc)
        val qucik_complain_status=view.findViewById<Button>(R.id.btn_go_complain)





        quick_machine.setOnClickListener {
            navigateToPostComplain("기계고장")
        }

        quick_public.setOnClickListener {
            navigateToPostComplain("공동시설")
        }

        quick_env.setOnClickListener {
            navigateToPostComplain("환경개선")
        }

        quick_talk.setOnClickListener {
            navigateToPostComplain("건의사항")
        }

        quick_etc.setOnClickListener {
            navigateToPostComplain("기타")
        }


        qucik_complain_status.setOnClickListener {
            val intent = Intent(activity, Community_Complain::class.java)
            startActivity(intent)
        }


        return view

    }

    private fun navigateToPostComplain(selectedItem: String) {
        val intent = Intent(activity, Post_Complain::class.java)
        // 선택된 항목을 인텐트에 추가합니다.
        intent.putExtra("selectedItem", selectedItem)
        startActivity(intent)
    }

    // 공지사항 !
    private fun fetchNoticeData() {
        val retrofit = RetrofitService.getService(requireContext())
        val service = retrofit.create(NoticeResponseDtoAPI::class.java)

        service.getAllNoticeResponseDto().enqueue(object : Callback<List<NoticeResponseDto>> {
            override fun onResponse(call: Call<List<NoticeResponseDto>>, response: Response<List<NoticeResponseDto>>) {
                if (response.isSuccessful) {
                    val notices = response.body()
                    notices?.let { noticeList ->
                        if (noticeList.isNotEmpty()) {
                            // 첫 번째 공지사항 설정
                            view?.let {
                                it.findViewById<TextView>(R.id.notice_title).text = noticeList[0].title
                                it.findViewById<TextView>(R.id.notice_contents).text = noticeList[0].contents
                            }
                        }
                    }

                } else {
                    // 서버 오류 등의 처리
                }
            }

            override fun onFailure(call: Call<List<NoticeResponseDto>>, t: Throwable) {
                // 네트워크 오류 등의 처리
            }
        })
    }


    private fun setFirstIssueData(view: View, post: PostsResponseDto) {
        view.findViewById<TextView>(R.id.recent_category1).text = post.category.toString()
        view.findViewById<TextView>(R.id.recent_title1).text = post.title
        view.findViewById<TextView>(R.id.recent_contents1).text = post.contents
    }

    private fun setSecondIssueData(view: View, post: PostsResponseDto) {
        view.findViewById<TextView>(R.id.recent_category2).text = post.category.toString()
        view.findViewById<TextView>(R.id.recent_title2).text = post.title
        view.findViewById<TextView>(R.id.recent_contents2).text = post.contents
    }


    // 게시글
    private fun fetchPostsData() {
        val retrofit = RetrofitService.getService(requireContext())
        val service = retrofit.create(PostsResponseDtoAPI::class.java)

        service.getAllPostsResponseDto().enqueue(object : Callback<List<PostsResponseDto>> {
            override fun onResponse(call: Call<List<PostsResponseDto>>, response: Response<List<PostsResponseDto>>) {
                if (response.isSuccessful) {
                    val posts = response.body()
                    posts?.let { postsList ->
                        if (postsList.size >= 2) {
                            // 첫 번째 게시글 설정
                            view?.let { setFirstIssueData(it, postsList[0]) }
                            // 두 번째 게시글 설정
                            view?.let { setSecondIssueData(it, postsList[1]) }
                        }
                    }

                } else {
                    // 서버 오류 등의 처리
                }
            }

            override fun onFailure(call: Call<List<PostsResponseDto>>, t: Throwable) {
                // 네트워크 오류 등의 처리
            }
        })
    }





    private fun getAllPosts() {
        val call = postsResponseDtoAPI.getAllPostsResponseDto()
        call.enqueue(object : Callback<List<PostsResponseDto>> {
            override fun onResponse(call: Call<List<PostsResponseDto>>, response: Response<List<PostsResponseDto>>) {
                if (!response.isSuccessful) {
                    return
                }

                val postIds = response.body()?.map { it.id } ?: return
                getPostDetails(postIds)
            }

            override fun onFailure(call: Call<List<PostsResponseDto>>, t: Throwable) {
                // Handle failure
            }
        })
    }

    // 우리건물이슈
    private fun getPostDetails(postIds: List<Long?>) {
        val postsDetails = mutableListOf<PostsResponseDto>()
        var processedCount = 0

        postIds.forEach { id ->
            id?.let {
                postsResponseDtoAPI.getPostById(it).enqueue(object : Callback<PostsResponseDto> {
                    override fun onResponse(call: Call<PostsResponseDto>, response: Response<PostsResponseDto>) {
                        if (response.isSuccessful) {
                            response.body()?.let { postDetail ->
                                if (postDetail.postsLikeCnt ?: 0 >= 10) {  // 필터링 조건 추가
                                    postsDetails.add(postDetail)
                                }
                            }
                        }
                        processedCount++
                        if (processedCount == postIds.size) {
                            updateRecyclerView(postsDetails)
                        }
                    }

                    override fun onFailure(call: Call<PostsResponseDto>, t: Throwable) {
                        processedCount++
                        if (processedCount == postIds.size) {
                            updateRecyclerView(postsDetails)
                        }
                    }
                })
            } ?: run {
                processedCount++
                if (processedCount == postIds.size) {
                    updateRecyclerView(postsDetails)
                }
            }
        }
    }

    private fun updateRecyclerView(postsDetails: List<PostsResponseDto>) {
        issueAdapter = IssueAdapter(postsDetails)
        recyclerView.adapter = issueAdapter

        issueAdapter.setOnItemClickListener(object : IssueAdapter.OnItemClickListener {
            override fun onItemClick(post: PostsResponseDto) {
                val intent = Intent(requireActivity(), Post_Detail_View::class.java).apply {
                    putExtra("POST_ID", post.id.toString())
                }
                startActivity(intent)

            }
        })
    }

}