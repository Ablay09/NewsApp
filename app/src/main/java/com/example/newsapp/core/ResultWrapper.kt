package com.example.newsapp.core

sealed class ResultWrapper<out T> {
    data class Success<out T>(val data: T): ResultWrapper<T>()
    data class Error(val exception: Throwable): ResultWrapper<Nothing>()
    object Loading: ResultWrapper<Nothing>()
}

val ResultWrapper<*>.succeeded
    get() = this is ResultWrapper.Success && data != null