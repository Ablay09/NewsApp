package com.example.newsapp.data

import com.example.newsapp.domain.news.Article
import com.example.newsapp.domain.news.ArticleSource
import com.example.newsapp.domain.news.NewsResult
import java.text.SimpleDateFormat
import java.util.*

private const val ISO_8604_FORMAT_WITH_TIMEZONE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"

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
            publishedAt = publishedAt.toFormattedString(),
            content = content
        )
    }

    fun ArticleSourceDTO.toDomain(): ArticleSource {
        return ArticleSource(
            id = id,
            name = name
        )
    }

    private fun Date.toFormattedString(): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy")
        return dateFormat.format(this)
    }
}