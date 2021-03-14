package com.example.newsapp.domain.news

import android.os.Parcelable
import com.example.newsapp.data.NewsStatus
import kotlinx.android.parcel.Parcelize
import java.util.*

data class NewsResult(
    val status: NewsStatus,
    val totalResults: Int,
    val articles: List<Article>
)

@Parcelize
data class Article(
    val source: ArticleSource,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?
) : Parcelable

@Parcelize
data class ArticleSource(
    val id: String?,
    val name: String
) : Parcelable
