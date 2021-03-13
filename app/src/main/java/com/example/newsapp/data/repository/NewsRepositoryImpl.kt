package com.example.newsapp.data.repository

import com.example.newsapp.api.NewsApi
import com.example.newsapp.core.CoroutineContextProvider
import com.example.newsapp.core.ResultWrapper
import com.example.newsapp.data.NewsMapper.toDomain
import com.example.newsapp.domain.news.NewsResult
import com.example.newsapp.domain.repository.NewsRepository

class NewsRepositoryImpl(
    private val api: NewsApi,
    private val coroutineContextProvider: CoroutineContextProvider
) : NewsRepository, BaseRepository() {

    override suspend fun fetchTopHeadlineNews(page: Int, pageSize: Int): ResultWrapper<NewsResult> {
        return safeApiCall(coroutineContextProvider.io) {
            api.fetchTopHeadlineNews(page = page, pageSize = pageSize).toDomain()
        }
    }

    override suspend fun fetchAllNews(page: Int, pageSize: Int): ResultWrapper<NewsResult> {
        return safeApiCall(coroutineContextProvider.io) {
            api.fetchAllNews(page = page, pageSize = pageSize).toDomain()
        }
    }

}