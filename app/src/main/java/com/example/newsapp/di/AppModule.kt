package com.example.newsapp.di

import com.example.newsapp.core.CoroutineContextProvider
import com.example.newsapp.core.CoroutineContextProviderImpl
import org.koin.dsl.module

val appModule = module {

    single<CoroutineContextProvider> {
        CoroutineContextProviderImpl()
    }
}