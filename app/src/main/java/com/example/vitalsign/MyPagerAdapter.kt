package com.example.vitalsign

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

//class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
class MyPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 2 // 페이지 수
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MainFragment() // 첫 번째 페이지 프래그먼트
            1 -> RoutineListFragment() // 두 번째 페이지 프래그먼트
            else -> Fragment()
        }
    }
}