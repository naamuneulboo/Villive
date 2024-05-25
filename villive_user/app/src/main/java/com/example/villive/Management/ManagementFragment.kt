package com.example.villive.Management

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.villive.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

private const val NUM_PAGES = 2

class ManagementFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private val tabTextList = arrayListOf("월세 관리", "차량 관리")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.management, container, false)

        viewPager = view.findViewById(R.id.mm_viewpager)
        tabLayout = view.findViewById(R.id.mm_tab)

        viewPager.adapter = ScreenSlidePagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTextList[position]
        }.attach()

        return view
    }

    private inner class ScreenSlidePagerAdapter(fa: Fragment) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment {
            return when(position){
                0 -> cost_frag()
                else -> car_frag()
            }
        }
    }
}