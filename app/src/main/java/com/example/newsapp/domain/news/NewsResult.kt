package com.example.newsapp.domain.news

import com.example.newsapp.data.NewsStatus
import java.util.*

data class NewsResult(
    val status: NewsStatus,
    val totalResults: Int,
    val articles: List<Article>
)

data class Article(
    val source: ArticleSource,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: Date,
    val content: String?
)


data class ArticleSource(
    val id: String?,
    val name: String
)
