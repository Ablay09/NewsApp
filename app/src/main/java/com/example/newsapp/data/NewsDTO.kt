package com.example.newsapp.data

import java.util.*

data class NewsResponseDTO(
    val status: String,
    val totalResults: Int,
    val articles: List<ArticleDTO>
)

enum class NewsStatus {
    OK, ERROR
}

data class ArticleDTO(
    val source: ArticleSourceDTO,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: Date,
    val content: String?
)

data class ArticleSourceDTO(
    val id: String?,
    val name: String
)