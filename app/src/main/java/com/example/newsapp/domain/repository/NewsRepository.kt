package com.example.newsapp.domain.repository

import com.example.newsapp.core.ResultWrapper
import com.example.newsapp.domain.news.NewsResult

interface NewsRepository {

    suspend fun getTopHeadlines(): ResultWrapper<NewsResult>

    suspend fun getAllNews(): ResultWrapper<NewsResult>
}