package com.example.newsapp.core.network

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInteceptor() : Interceptor {

    companion object {
        private const val API_KEY = "e65ee0938a2a43ebb15923b48faed18d"
    }
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val requestWithApiKey = request.newBuilder()
            .addHeader("Authorization", API_KEY)
            .build()

        return chain.proceed(requestWithApiKey)
    }

}