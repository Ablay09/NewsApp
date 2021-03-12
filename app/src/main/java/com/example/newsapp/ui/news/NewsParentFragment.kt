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
import kotlinx.android.synthetic.main.fragment_news_parent.*

class NewsParentFragment : Fragment() {

/*
    private var param1: String? = null
    private var param2: String? = null
*/

    private val adapter: NewsPagerAdapter by lazy {
        NewsPagerAdapter(requireActivity())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news_parent, container, false)
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        /*@JvmStatic fun newInstance(param1: String, param2: String) =
                NewsParentFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }*/
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
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