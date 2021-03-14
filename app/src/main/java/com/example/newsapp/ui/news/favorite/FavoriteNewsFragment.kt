package com.example.newsapp.ui.news.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.domain.news.Article
import com.example.newsapp.ui.MainActivity
import com.example.newsapp.ui.news.NewsViewModel
import com.example.newsapp.ui.news.adapter.NewsListAdapter
import com.example.newsapp.ui.news.adapter.NewsPagedListAdapter
import com.example.newsapp.ui.news.details.NewsDetailsFragment
import kotlinx.android.synthetic.main.fragment_favorite_news.*
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteNewsFragment : Fragment() {

    private val viewModel: NewsViewModel by viewModel()

    private val adapter: NewsListAdapter by lazy {
        NewsListAdapter(::handleNewsItemClicked)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllFavoriteNews()
        initRecyclerView()
        setListeners()
        observeViewModel()
    }

    private fun initRecyclerView() {
        rvFavoriteNews.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.allFavoriteNews.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                tvEmptyState.visibility = View.VISIBLE
            } else {
                tvEmptyState.visibility = View.GONE
                adapter.submitList(it)
            }

        }
    }

    private fun handleNewsItemClicked(article: Article) {
        val fragment = NewsDetailsFragment().apply {
            arguments = NewsDetailsFragment.bundle(article)
        }
        (requireActivity() as MainActivity).addFragment(fragment)
    }

    private fun setListeners() {
        btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

}