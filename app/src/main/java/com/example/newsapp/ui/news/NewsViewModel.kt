package com.example.newsapp.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.example.newsapp.data.paging.AllNewsDataSourceFactory
import com.example.newsapp.data.paging.PagingDataSource
import com.example.newsapp.data.paging.PagingState
import com.example.newsapp.data.paging.TopNewsDataSourceFactory
import com.example.newsapp.domain.news.Article
import com.example.newsapp.domain.repository.NewsRepository

class NewsViewModel(newsRepository: NewsRepository) : ViewModel() {

    private val topNewsDataSourceFactory = TopNewsDataSourceFactory(newsRepository)
    val topNewsPagingState: LiveData<PagingState> =
        topNewsDataSourceFactory.sourceLiveData.switchMap {
            it.pagingState
        }
    val topHeadlinesLiveData: LiveData<PagedList<Article>> =
        topNewsDataSourceFactory.toLiveData(PagingDataSource.config)

    private val allNewsDataSourceFactory = AllNewsDataSourceFactory(newsRepository)
    val allNewsPagingState = allNewsDataSourceFactory.sourceLiveData.switchMap {
        it.pagingState
    }
    val allNewsLiveData: LiveData<PagedList<Article>> =
        allNewsDataSourceFactory.toLiveData(PagingDataSource.config)


    fun fetchTopHeadlineNews() = topHeadlinesLiveData

    fun fetchAllNews() = allNewsLiveData
}