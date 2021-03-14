package com.example.newsapp.data.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.newsapp.domain.news.Article
import com.example.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.CoroutineScope

class AllNewsDataSourceFactory(
    private val repository: NewsRepository
) : DataSource.Factory<Int, Article>() {

    val sourceLiveData = MutableLiveData<AllNewsDataSource>()

    override fun create(): DataSource<Int, Article> {
        val newSource = AllNewsDataSource(repository)
        sourceLiveData.postValue(newSource)
        return newSource
    }

    fun refresh() {
        sourceLiveData.value?.invalidate()
    }
}