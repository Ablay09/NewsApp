package com.example.newsapp.ui.news.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newsapp.ui.news.NewsEverythingFragment
import com.example.newsapp.ui.news.NewsTopHeadlinesFragment

class NewsPagerAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    companion object {
        private const val TAB_COUNT = 2

        const val TOP_HEADLINES_TAB = 0
        const val EVERYTHING_TAB = 1

    }

    override fun getItemCount(): Int = TAB_COUNT

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            TOP_HEADLINES_TAB -> NewsTopHeadlinesFragment()
            EVERYTHING_TAB -> NewsEverythingFragment()
            else -> throw IllegalStateException("Invalid adapter position")
        }
    }
}