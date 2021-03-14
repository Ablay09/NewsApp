package com.example.newsapp.ui.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newsapp.R
import com.example.newsapp.ui.news.adapter.NewsPagerAdapter
import com.example.newsapp.ui.news.adapter.NewsPagerAdapter.Companion.EVERYTHING_TAB
import com.example.newsapp.ui.news.adapter.NewsPagerAdapter.Companion.TOP_HEADLINES_TAB
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_news_details.*
import kotlinx.android.synthetic.main.fragment_news_parent.*
import kotlinx.android.synthetic.main.fragment_news_parent.tvToolbarText

class NewsParentFragment : Fragment() {

    private val adapter: NewsPagerAdapter by lazy {
        NewsPagerAdapter(requireActivity())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news_parent, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        tvToolbarText.text = getString(R.string.app_name)
    }

    private fun initViewPager() {
        newsViewPager.adapter = adapter
        TabLayoutMediator(newsTabs, newsViewPager) { tab, position ->
            tab.text = when (position) {
                TOP_HEADLINES_TAB -> getString(R.string.news_top_headlines)
                EVERYTHING_TAB -> getString(R.string.news_everything)
                else -> throw IllegalStateException("Invalid tabLayout position")
            }
        }.attach()
    }

}