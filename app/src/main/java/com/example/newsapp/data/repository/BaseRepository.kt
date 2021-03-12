package com.example.newsapp.data.repository

import com.example.newsapp.core.ResultWrapper
import com.example.newsapp.core.network.NetworkException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.IOException

open class BaseRepository {

    protected suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): ResultWrapper<T> {
        return withContext(dispatcher) {
            try {
                ResultWrapper.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                ResultWrapper.Error(throwable)
            }
        }
    }
}