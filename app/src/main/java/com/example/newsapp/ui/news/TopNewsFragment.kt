package com.example.newsapp.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.paging.PagedList
import com.example.newsapp.R
import com.example.newsapp.core.extensions.getErrorMessage
import com.example.newsapp.core.extensions.showToast
import com.example.newsapp.data.paging.PagingState
import com.example.newsapp.domain.news.Article
import com.example.newsapp.ui.news.adapter.NewsListAdapter
import kotlinx.android.synthetic.main.fragment_top_news.*
import org.koin.android.viewmodel.ext.android.viewModel

class TopNewsFragment : Fragment() {

    private val viewModel: NewsViewModel by viewModel()
    private val adapter: NewsListAdapter by lazy {
        NewsListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_top_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchTopHeadlineNews()
        initRecyclerView()
        observeViewModel()
    }

    private fun initRecyclerView() {
        rvTopHeadlineNews.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.topNewsPagingState.observe(viewLifecycleOwner) { pagingState ->
            when (pagingState) {
                is PagingState.Error -> showToastMessage(
                    requireContext().getErrorMessage(pagingState.exception)
                )
                else -> Unit
            }
        }
        viewModel.topHeadlinesLiveData.observe(viewLifecycleOwner, ::handleNewsResult)
    }

    private fun handleNewsResult(news: PagedList<Article>) {
        adapter.submitList(news)
    }

    private fun showToastMessage(message: String) {
        requireContext().showToast(message)
    }
}