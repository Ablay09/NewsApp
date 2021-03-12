package com.example.newsapp.data

import com.example.newsapp.domain.news.Article
import com.example.newsapp.domain.news.ArticleSource
import com.example.newsapp.domain.news.NewsResult

object NewsMapper {

    fun NewsResponseDTO.toDomain(): NewsResult {
        return NewsResult(
            status = status.toNewsStatus(),
            totalResults = totalResults,
            articles = articles.map { it.toDomain() }
        )
    }

    fun String.toNewsStatus(): NewsStatus {
        return NewsStatus.valueOf(this.toUpperCase())
    }

    fun ArticleDTO.toDomain(): Article {
        return Article(
            source = source.toDomain(),
            author = author,
            title = title,
            description = description,
            url = url,
            urlToImage = urlToImage,
            publishedAt = publishedAt,
            content = content
        )
    }

    fun ArticleSourceDTO.toDomain(): ArticleSource {
        return ArticleSource(
            id = id,
            name = name
        )
    }
}