package com.example.newsapp.data.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ArticleEntity(
    @PrimaryKey
    val title: String,
    @Embedded
    val source: ArticleSourceEntity,
    val author: String?,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?
)

data class ArticleSourceEntity(
    val id: String?,
    val name: String
)