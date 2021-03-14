package com.example.newsapp.ui.news.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.core.extensions.load
import com.example.newsapp.domain.news.Article
import com.example.newsapp.ui.news.NewsViewModel
import kotlinx.android.synthetic.main.fragment_news_details.*
import org.koin.android.viewmodel.ext.android.viewModel

class NewsDetailsFragment : Fragment() {

    companion object {
        private const val ARG_NEWS_DETAILS = "news_details"

        fun bundle(article: Article): Bundle = bundleOf(
            ARG_NEWS_DETAILS to article
        )
    }

    private val viewModel: NewsViewModel by viewModel()

    private val newsDetails: Article by lazy {
        arguments?.getParcelable<Article>(ARG_NEWS_DETAILS) ?:
            throw IllegalArgumentException("Article is required in ${this::class.simpleName} bundle")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setListeners()
    }

    private fun initViews() {
        tvToolbarText.text = getString(R.string.details)
        with(newsDetails) {
            ivNews.load(urlToImage)
            tvSourceTitle.text = source.name
            tvNewsTitle.text = title
            tvNewsContent.text = description
            tvPublishedDate.text = publishedAt
            btnAddToFavorites.isSelected = isFavorite
        }
        initWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        webView.settings.javaScriptEnabled = true
        webView.settings.loadsImagesAutomatically = true
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        webView.loadUrl(newsDetails.url)
    }

    private fun setListeners() {
        btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        btnAddToFavorites.setOnClickListener {
            if (!newsDetails.isFavorite) {
                btnAddToFavorites.isSelected = true
                viewModel.addToFavorites(newsDetails)
            } else {
                // TODO delete from Favorites
            }
        }
    }
}