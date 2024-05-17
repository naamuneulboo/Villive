package com.example.villive

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.example.villive.Community.Community
import com.example.villive.Community.Community_NoticeTab
import com.example.villive.Community.Community_Purchase
import com.example.villive.Community.Complain_status
import com.example.villive.Community_Write.Post_Complain
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment() {

    private val eventList = arrayListOf("캔/병", "일반", "음식물", "종이","플라스틱")

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


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
        val view = inflater.inflate(R.layout.home, container, false)

        val eventText = dayOfYear%eventList.size

        view.findViewById<TextView>(R.id.date_tv).text = currentDateInfo
        view.findViewById<TextView>(R.id.event_tv).text = eventList[eventText] + " 배출일"

        val boardLayout = view.findViewById<LinearLayout>(R.id.board_lo)

        boardLayout.setOnClickListener {
            val intent = Intent(activity, Community::class.java)
            startActivity(intent)
        }


        val alarm_button = view.findViewById<ImageButton>(R.id.noti_button)
        val quick_machine = view.findViewById<Button>(R.id.btn_quick_machine)
        val quick_public = view.findViewById<Button>(R.id.btn_quick_public)
        val quick_env = view.findViewById<Button>(R.id.btn_quick_env)
        val quick_talk = view.findViewById<Button>(R.id.btn_quick_talk)
        val quick_etc = view.findViewById<Button>(R.id.btn_quick_etc)
        val qucik_complain_status=view.findViewById<Button>(R.id.btn_go_complain)



        // 항목이 자동으로 선택되게 아직 못함
        fun navigateToPostComplain(selectedItem: String) {
            val intent = Intent(activity, Post_Complain::class.java)
            intent.putExtra("selectedItem", selectedItem)
            startActivity(intent)
        }

        alarm_button.setOnClickListener {
            val intent = Intent(activity, Community_NoticeTab::class.java)
            startActivity(intent)
        }

        quick_machine.setOnClickListener {
            navigateToPostComplain("기계 고장")
        }

        quick_public.setOnClickListener {
            navigateToPostComplain("공동 시설")
        }

        quick_env.setOnClickListener {
            navigateToPostComplain("환경 개선")
        }

        quick_talk.setOnClickListener {
            navigateToPostComplain("건의 사항")
        }

        quick_etc.setOnClickListener {
            navigateToPostComplain("기타 민원")
        }


        qucik_complain_status.setOnClickListener {
            val intent = Intent(activity, Complain_status::class.java)
            startActivity(intent)
        }


        return view

    }




}