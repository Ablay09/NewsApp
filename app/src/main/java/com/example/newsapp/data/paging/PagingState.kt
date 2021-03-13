package com.example.newsapp.data.paging

sealed class PagingState {
    object Loading : PagingState()
    object Success : PagingState()
    data class Error(val exception: Throwable) : PagingState()
}
