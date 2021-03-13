package com.example.newsapp.data.paging

import androidx.paging.PagedList

object PagingDataSource {

    const val FIRST_PAGE = 1
    const val PAGE_SIZE = 15
    const val PREFETCH_DISTANCE = PAGE_SIZE / 5

    val config = PagedList.Config.Builder()
        .setPageSize(PAGE_SIZE)
        .setPrefetchDistance(PREFETCH_DISTANCE)
        .setInitialLoadSizeHint(PAGE_SIZE)
        .setEnablePlaceholders(false)
        .build()
}