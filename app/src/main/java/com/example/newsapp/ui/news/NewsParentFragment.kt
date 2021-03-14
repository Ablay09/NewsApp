package com.example.newsapp.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.ui.MainActivity
import com.example.newsapp.ui.news.adapter.NewsPagerAdapter
import com.example.newsapp.ui.news.adapter.NewsPagerAdapter.Companion.EVERYTHING_TAB
import com.example.newsapp.ui.news.adapter.NewsPagerAdapter.Companion.TOP_HEADLINES_TAB
import com.example.newsapp.ui.news.favorite.FavoriteNewsFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_news_parent.*

class NewsParentFragment : Fragment() {

    private val adapter: NewsPagerAdapter by lazy {
        NewsPagerAdapter(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_parent, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        tvToolbarText.text = getString(R.string.app_name)
        setListeners()
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

    private fun setListeners() {
        btnFavorite.setOnClickListener {
            val fragment = FavoriteNewsFragment()

            (requireActivity() as MainActivity).addFragment(fragment)
        }
    }

}