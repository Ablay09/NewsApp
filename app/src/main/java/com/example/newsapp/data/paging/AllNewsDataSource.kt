package com.example.newsapp.data.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.newsapp.core.ResultWrapper
import com.example.newsapp.data.paging.PagingDataSource.PAGE_SIZE
import com.example.newsapp.data.paging.PagingDataSource.FIRST_PAGE
import com.example.newsapp.domain.news.Article
import com.example.newsapp.domain.news.NewsResult
import com.example.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class AllNewsDataSource(private val repository: NewsRepository) :
    PageKeyedDataSource<Int, Article>()/*, CoroutineScope*/ {

    private val _pagingState = MutableLiveData<PagingState>()
    val pagingState: LiveData<PagingState>
        get() = _pagingState

    private val scope = CoroutineScope(Dispatchers.IO + Job())

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Article>
    ) {
        scope.launch {
            val response: ResultWrapper<NewsResult> = repository.fetchAllNews(
                page = FIRST_PAGE,
                pageSize = PAGE_SIZE
            )

            when (response) {
                is ResultWrapper.Success -> {
                    val list = mutableListOf<Article>()
                    list.addAll(response.data.articles)

                    val nextPage = getNextPage(list, FIRST_PAGE)
                    callback.onResult(list, null, nextPage)
                    _pagingState.postValue(PagingState.Success)
                }
                is ResultWrapper.Error -> {
                    _pagingState.postValue(PagingState.Error(response.exception))
                }
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        val page = params.key
        _pagingState.postValue(PagingState.Loading)
        scope.launch {
            val response = repository.fetchAllNews(page = page, pageSize = PAGE_SIZE)
            when (response) {
                is ResultWrapper.Success -> {
                    val list = response.data.articles
                    val nextPage = getNextPage(response.data.articles, page)
                    callback.onResult(list, nextPage)
                    _pagingState.postValue(PagingState.Success)
                }
                is ResultWrapper.Error -> {
                    _pagingState.postValue(PagingState.Error(response.exception))
                }
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) = Unit

    private fun getNextPage(articles: List<Article>, page: Int): Int? {
        return if (articles.size < PAGE_SIZE) {
            null
        } else {
            page + 1
        }
    }

    override fun invalidate() {
        super.invalidate()
        scope.cancel()
    }
/*
    override val coroutineContext: CoroutineContext
        get() = Job()*/
}