package com.example.newsapp.di

import com.example.newsapp.data.repository.FavoriteNewsRepositoryImpl
import com.example.newsapp.data.repository.NewsRepositoryImpl
import com.example.newsapp.domain.repository.FavoriteNewsRepository
import com.example.newsapp.domain.repository.NewsRepository
import com.example.newsapp.ui.news.NewsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val newsModule = module {

    single<NewsRepository> {
        NewsRepositoryImpl(
            api = get(),
            coroutineContextProvider = get()
        )
    }

    viewModel {
        NewsViewModel(
            favoriteNewsRepository = get(),
            coroutineContextProvider = get(),
            newsRepository = get()
        )
    }

    single<FavoriteNewsRepository> { FavoriteNewsRepositoryImpl(get()) }
}