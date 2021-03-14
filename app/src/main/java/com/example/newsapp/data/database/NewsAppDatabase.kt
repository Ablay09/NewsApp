package com.example.newsapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsapp.data.database.dao.NewsDAO
import com.example.newsapp.data.database.entity.ArticleEntity
import com.example.newsapp.data.database.entity.ArticleSourceEntity

@Database(entities = arrayOf(ArticleEntity::class), version = 1)
abstract class NewsAppDatabase : RoomDatabase() {
    abstract fun newsDao() : NewsDAO
}