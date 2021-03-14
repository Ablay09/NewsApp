package com.example.newsapp

import android.app.Application
import com.example.newsapp.di.networkModule
import com.example.newsapp.di.newsModule
import com.example.newsapp.di.appModule
import com.example.newsapp.di.databaseModule
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
                    networkModule,
                    newsModule,
                    appModule,
                    databaseModule
                )
            )

        }
    }
}