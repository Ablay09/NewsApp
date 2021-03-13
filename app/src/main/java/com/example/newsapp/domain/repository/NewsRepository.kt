package com.example.newsapp.domain.repository

import com.example.newsapp.core.ResultWrapper
import com.example.newsapp.domain.news.NewsResult

interface NewsRepository {

    suspend fun fetchTopHeadlineNews(page: Int, pageSize: Int): ResultWrapper<NewsResult>

    suspend fun fetchAllNews(page: Int, pageSize: Int): ResultWrapper<NewsResult>
}