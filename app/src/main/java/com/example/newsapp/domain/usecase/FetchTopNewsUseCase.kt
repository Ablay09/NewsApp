package com.example.newsapp.domain.usecase

import com.example.newsapp.core.ResultWrapper
import com.example.newsapp.domain.news.NewsResult
import com.example.newsapp.domain.repository.NewsRepository

class FetchTopNewsUseCase(private val repository: NewsRepository) {

    suspend operator fun invoke(page: Int, pageSize: Int): ResultWrapper<NewsResult> {
        return repository.fetchTopHeadlineNews(page, pageSize)
    }
}