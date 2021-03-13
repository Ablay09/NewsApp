package com.example.newsapp.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.core.extensions.getErrorMessage
import com.example.newsapp.core.extensions.showToast
import com.example.newsapp.domain.news.Article
import com.example.newsapp.ui.news.adapter.NewsListAdapter
import kotlinx.android.synthetic.main.fragment_news_everything.*
import org.koin.android.viewmodel.ext.android.viewModel

class NewsEverythingFragment : Fragment() {
    private val viewModel: NewsViewModel by viewModel()
    private val adapter: NewsListAdapter by lazy {
        NewsListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_everything, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchAllNews()
        initRecyclerView()
        observeViewModel()
        setListeners()
    }

    private fun initRecyclerView() {
        rvAllNews.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.allNewsLiveData.observe(viewLifecycleOwner, ::handleNewsResult)
    }

    private fun handleNewsResult(result: Result<List<Article>>) {
        swipeRefresh.isRefreshing = false
        result
            .onFailure { throwable -> showToastMessage(requireContext().getErrorMessage(throwable)) }
            .onSuccess { result -> adapter.submitList(result) }
    }

    private fun setListeners() {
        swipeRefresh.setOnRefreshListener {
            viewModel.fetchAllNews()
        }
    }

    private fun showToastMessage(message: String) {
        requireContext().showToast(message)
    }
}