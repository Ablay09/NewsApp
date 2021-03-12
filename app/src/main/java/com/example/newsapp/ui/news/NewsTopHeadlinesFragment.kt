package com.example.newsapp.ui.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.core.extensions.getErrorMessage
import com.example.newsapp.core.extensions.showToast
import org.koin.android.viewmodel.ext.android.viewModel

class NewsTopHeadlinesFragment : Fragment() {

    private val viewModel: NewsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_top_headlines, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchTopHeadlineNews()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.topHeadlinesLiveData.observe(viewLifecycleOwner) {
            it
                .onFailure { throwable -> showToastMessage(requireContext().getErrorMessage(throwable)) }
                .onSuccess { result -> Log.d(this::class.simpleName, "$result") }
        }
    }

    private fun showToastMessage(message: String) {
        requireContext().showToast(message)
    }
}