package com.example.newsapp.data.database.dao

import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.data.database.entity.ArticleEntity

@Dao
abstract class NewsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun addToFavorites(article: ArticleEntity)

    @Query("SELECT * FROM articleentity")
    abstract fun getAllFavoriteArticles(): List<ArticleEntity>

    @Query("SELECT EXISTS (SELECT 1 FROM articleentity WHERE title = :title)")
    abstract fun isArticleExist(title: String): Boolean
}