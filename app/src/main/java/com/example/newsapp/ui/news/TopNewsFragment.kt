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
import com.example.newsapp.ui.MainActivity
import com.example.newsapp.ui.news.adapter.NewsPagedListAdapter
import com.example.newsapp.ui.news.details.NewsDetailsFragment
import kotlinx.android.synthetic.main.fragment_top_news.*
import org.koin.android.viewmodel.ext.android.viewModel

class TopNewsFragment : Fragment() {

    private val viewModel: NewsViewModel by viewModel()
    private val adapter: NewsPagedListAdapter by lazy {
        NewsPagedListAdapter(::handleNewsItemClicked)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_top_news, container, false)
    }

    override fun onResume() {
        super.onResume()
//        viewModel.startUpdates()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeViewModel()
    }

    private fun initRecyclerView() {
        rvTopHeadlineNews.adapter = adapter
        rvTopHeadlineNews.setHasFixedSize(true)
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
        viewModel.topNewsLiveData.observe(viewLifecycleOwner, ::handleNewsResult)
    }

    private fun handleNewsResult(news: PagedList<Article>) {
        adapter.submitList(news)
    }

    private fun showToastMessage(message: String) {
        requireContext().showToast(message)
    }

    private fun handleNewsItemClicked(article: Article) {
        val fragment = NewsDetailsFragment().apply {
            arguments = NewsDetailsFragment.bundle(article)
        }
        (requireActivity() as MainActivity).addFragment(fragment)
    }

    override fun onPause() {
        super.onPause()
//        viewModel.stopUpdates()
    }
}