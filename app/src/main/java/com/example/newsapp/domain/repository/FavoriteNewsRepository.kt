package com.example.newsapp.domain.repository

import com.example.newsapp.data.database.entity.ArticleEntity
import com.example.newsapp.domain.news.Article

interface FavoriteNewsRepository {

    fun insert(article: ArticleEntity)

    fun isFavorite(title: String): Boolean

    fun getAllNews(): List<Article>
}