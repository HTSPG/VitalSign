package com.example.vitalsign

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.vitalsign.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        // ViewPager 어댑터 설정
//        binding.viewPager.adapter = MyPagerAdapter(supportFragmentManager)
//
//        // TabLayout과 ViewPager 연동
//        binding.tabLayout.setupWithViewPager(binding.viewPager)

        // ViewPager2 어댑터 설정
        binding.viewPager.adapter = MyPagerAdapter(this)

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                for (i in 0 until binding.tabLayout.tabCount) {
                    val tab = binding.tabLayout.getTabAt(i)
                    tab?.let {
                        setTabDotColor(it, i == position)
                    }
                }
            }
        })

        // TabLayout과 ViewPager2 연동
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            val customTabView = LayoutInflater.from(this).inflate(R.layout.custom_tab_dot, null)
            tab.customView = customTabView

            // 초기 탭 색상 설정
            setTabDotColor(tab, position == 0)
        }.attach()
    }

    fun setTabDotColor(tab: TabLayout.Tab, isActive: Boolean) {
        val color = if (isActive) R.color.accent else R.color.normal
        tab.customView?.findViewById<View>(R.id.dotView)?.setBackgroundResource(color)
    }
}

