package com.example.newsapp.di

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.example.newsapp.BuildConfig
import com.example.newsapp.api.NewsApi
import com.example.newsapp.core.network.ApiKeyInteceptor
import com.example.newsapp.core.network.ErrorMappingInterceptor
import com.example.newsapp.core.network.NetworkHandler
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

val networkModule = module {

    single {
        Gson()
    }

    single<ApiKeyInteceptor> {
        ApiKeyInteceptor()
    }

    single<ErrorMappingInterceptor> {
        ErrorMappingInterceptor(
            networkHandler = get(),
            gson = get()
        )
    }

    single<NetworkHandler> {
        NetworkHandler(androidContext())
    }
    single<ChuckerInterceptor> {
        val chuckerCollector = ChuckerCollector(
            context = androidContext(),
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )
        ChuckerInterceptor.Builder(androidContext())
            .collector(chuckerCollector)
            .build()
    }

    single<HttpLoggingInterceptor> {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .apply {
                if (BuildConfig.BUILD_TYPE == "debug") {
                    addInterceptor(get<HttpLoggingInterceptor>())
                    addInterceptor(get<ChuckerInterceptor>())
                }
            }
            .addInterceptor(get<ErrorMappingInterceptor>())
            .addInterceptor(get<ApiKeyInteceptor>())
            .followRedirects(false)
            .build()
    }

    single<Retrofit> {
        val okHttpClient = get<OkHttpClient>()
        Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    single<NewsApi> {
        get(Retrofit::class.java).create(NewsApi::class.java)
    }
}