package com.example.newsapp.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.core.ResultWrapper
import com.example.newsapp.domain.news.Article
import com.example.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.launch

class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _topHeadlinesLiveData = MutableLiveData<Result<List<Article>>>()
    val topHeadlinesLiveData: LiveData<Result<List<Article>>>
        get() = _topHeadlinesLiveData

    fun fetchTopHeadlineNews() {
        viewModelScope.launch {
            val newsResponse = newsRepository.getTopHeadlines()
            when (newsResponse) {
                is ResultWrapper.Error -> {
                    _topHeadlinesLiveData.value = Result.failure(newsResponse.exception)
                }
                is ResultWrapper.Success -> {
                    _topHeadlinesLiveData.value = Result.success(newsResponse.data.articles)
                }
            }
        }
    }
}