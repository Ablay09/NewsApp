package com.example.newsapp.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.example.newsapp.data.paging.AllNewsDataSourceFactory
import com.example.newsapp.data.paging.PagingDataSource
import com.example.newsapp.data.paging.PagingState
import com.example.newsapp.data.paging.TopNewsDataSourceFactory
import com.example.newsapp.domain.news.Article
import com.example.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewsViewModel(
    newsRepository: NewsRepository
) : ViewModel() {

    companion object {
        private const val DELAY_TIME = 5000L
    }

    private var job: Job? = null

    private val topNewsDataSourceFactory = TopNewsDataSourceFactory(newsRepository)
    val topNewsPagingState: LiveData<PagingState> =
        topNewsDataSourceFactory.sourceLiveData.switchMap {
            it.pagingState
        }
    val topNewsLiveData: LiveData<PagedList<Article>> =
        topNewsDataSourceFactory.toLiveData(PagingDataSource.config)

    private val allNewsDataSourceFactory = AllNewsDataSourceFactory(newsRepository)
    val allNewsPagingState = allNewsDataSourceFactory.sourceLiveData.switchMap {
        it.pagingState
    }
    val allNewsLiveData: LiveData<PagedList<Article>> =
        allNewsDataSourceFactory.toLiveData(PagingDataSource.config)


    fun fetchAllNews() = allNewsLiveData

    fun onRefreshAllNews() {
        allNewsDataSourceFactory.refresh()
    }

    fun startUpdates() {
        job = viewModelScope.launch {
            while (true) {
                topNewsDataSourceFactory.refresh()
                delay(DELAY_TIME)
            }
        }
    }

    fun stopUpdates() {
        job?.cancel()
        job = null
    }

    override fun onCleared() {
        job?.cancel()
        super.onCleared()
    }

}