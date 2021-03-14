package com.example.newsapp.data.repository

import com.example.newsapp.data.database.dao.NewsDAO
import com.example.newsapp.data.database.entity.ArticleEntity
import com.example.newsapp.domain.news.Article
import com.example.newsapp.domain.news.toArticle
import com.example.newsapp.domain.repository.FavoriteNewsRepository

class FavoriteNewsRepositoryImpl(
    private val newsDAO: NewsDAO
) : FavoriteNewsRepository {

    override fun insert(article: ArticleEntity) {
        newsDAO.addToFavorites(article)
    }

    override fun isFavorite(title: String): Boolean {
        return newsDAO.isArticleExist(title)
    }

    override fun getAllNews(): List<Article> {
        return newsDAO.getAllFavoriteArticles().map {
            it.toArticle()
        }
    }
}