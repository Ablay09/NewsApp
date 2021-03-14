package com.example.newsapp.di

import androidx.room.Room
import com.example.newsapp.data.database.NewsAppDatabase
import com.example.newsapp.data.database.dao.NewsDAO
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {

    single<NewsAppDatabase> {
        Room.databaseBuilder(
            androidContext(),
            NewsAppDatabase::class.java,
            "news_app.db"
        ).build()
    }

    single<NewsDAO> { get<NewsAppDatabase>().newsDao() }
}