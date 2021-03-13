package com.example.newsapp.api

import com.example.newsapp.data.NewsResponseDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun fetchTopHeadlineNews(
        @Query("q") query: String = "trump"
    ): NewsResponseDTO

    @GET("v2/everything")
    suspend fun fetchAllNews(
        @Query("q") query: String = "trump"
    ): NewsResponseDTO
}