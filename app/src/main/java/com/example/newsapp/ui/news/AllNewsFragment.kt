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
import kotlinx.android.synthetic.main.fragment_all_news.*
import org.koin.android.viewmodel.ext.android.viewModel

class AllNewsFragment : Fragment() {
    private val viewModel: NewsViewModel by viewModel()
    private val adapter: NewsListAdapter by lazy {
        NewsListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_all_news, container, false)
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
        viewModel.allNewsPagingState.observe(viewLifecycleOwner, ::handlePagingState)
    }

    private fun handleNewsResult(result: PagedList<Article>) {
        adapter.submitList(result)
    }

    private fun handlePagingState(state: PagingState) {
        swipeRefresh.isRefreshing = state is PagingState.Loading
        when (state) {
            is PagingState.Error -> showToastMessage(
                requireContext().getErrorMessage(state.exception)
            )
            else -> Unit
        }
    }

    private fun setListeners() {
        swipeRefresh.setOnRefreshListener {
            viewModel.allNewsLiveData.value?.dataSource?.invalidate()
        }
    }

    private fun showToastMessage(message: String) {
        requireContext().showToast(message)
    }
}