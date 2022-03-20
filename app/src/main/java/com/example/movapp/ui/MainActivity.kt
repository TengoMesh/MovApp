package com.example.movapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.movapp.R
import com.example.movapp.data.FragmentPagerData
import com.example.movapp.data.ViewPagerFragmentProvider
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewPager()
    }

    private fun setupViewPager(){
        val viewPager = findViewById<ViewPager2>(R.id.uViewPager)
        val tabLayout = findViewById<TabLayout>(R.id.uTabLayout)

        val fragmentList = FragmentPagerData.getMovieFragmentProviderList()

        viewPager.adapter = MoviePagerAdapter(fragmentList,this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = fragmentList[position].getName()
        }.attach()
    }
}

class MoviePagerAdapter(
    private val fragmentList: List<ViewPagerFragmentProvider>,
    fragmentActivity: FragmentActivity
): FragmentStateAdapter(fragmentActivity){
    override fun getItemCount() = fragmentList.size

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position].createFragment()
    }
}
