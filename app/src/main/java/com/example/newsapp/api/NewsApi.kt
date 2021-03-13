package com.example.newsapp.api

import com.example.newsapp.data.NewsResponseDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun fetchTopHeadlineNews(
        @Query("q", encoded = true) query: String = "a",
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): NewsResponseDTO

    @GET("v2/everything")
    suspend fun fetchAllNews(
        @Query("q", encoded = true) query: String = "a",
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): NewsResponseDTO
}