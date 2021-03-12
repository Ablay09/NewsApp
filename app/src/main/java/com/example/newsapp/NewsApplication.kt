package com.example.newsapp

import android.app.Application
import com.example.newsapp.di.NetworkModule
import com.example.newsapp.di.NewsModule
import com.example.newsapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NewsApplication)
            androidLogger()
            modules(
                listOf(
                    NetworkModule,
                    NewsModule,
                    appModule
                )
            )

        }
    }
}