package com.example.villive.Alarm

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.villive.Alarm.Board_Alarm
import com.example.villive.Alarm.Notice_Alarm
import com.example.villive.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

private const val NUM_PAGES = 2

class AlarmFragment : Fragment() {

    private lateinit var al_viewPager: ViewPager2
    private lateinit var al_tabLayout: TabLayout
    private val tabTextList2 = arrayListOf("공지사항", "게시판")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.alarm_page, container, false)

        al_viewPager = view.findViewById(R.id.al_viewpager)
        al_tabLayout = view.findViewById(R.id.al_tab)

        al_viewPager.adapter = ViewPagerAdapter(this)

        TabLayoutMediator(al_tabLayout, al_viewPager) { tab, position ->
            tab.text = tabTextList2[position]
        }.attach()

        return view
    }

    private inner class ViewPagerAdapter(fa: Fragment) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment {
            return when(position){
                0 -> Notice_Alarm()
                else -> Board_Alarm()
            }
        }
    }
}