package com.example.newsapp.domain.news

import android.os.Parcelable
import com.example.newsapp.data.NewsStatus
import com.example.newsapp.data.database.entity.ArticleEntity
import com.example.newsapp.data.database.entity.ArticleSourceEntity
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
    val content: String?,
    val isFavorite: Boolean = false
) : Parcelable

@Parcelize
data class ArticleSource(
    val id: String?,
    val name: String
) : Parcelable

fun Article.toEntity(): ArticleEntity {
    return ArticleEntity(
        title = title,
        source = source.toEntity(),
        author = author,
        description = description,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content
    )
}

fun ArticleEntity.toArticle(): Article {
    return Article(
        source = source.toSource(),
        author = author,
        description = description,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content,
        title = title,
        isFavorite = true
    )
}


fun ArticleSourceEntity.toSource(): ArticleSource {
    return ArticleSource(
        id = id,
        name = name
    )
}

fun ArticleSource.toEntity(): ArticleSourceEntity {
    return ArticleSourceEntity(
        id = id,
        name = name
    )
}