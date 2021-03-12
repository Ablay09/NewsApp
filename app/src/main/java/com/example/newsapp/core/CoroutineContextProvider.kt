package com.example.newsapp.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface CoroutineContextProvider {
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val ui: CoroutineDispatcher
}

class CoroutineContextProviderImpl() : CoroutineContextProvider {
    override val io = Dispatchers.IO
    override val default = Dispatchers.Default
    override val ui: CoroutineDispatcher = Dispatchers.Main
}