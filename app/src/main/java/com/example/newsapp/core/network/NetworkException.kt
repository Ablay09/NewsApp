package com.example.newsapp.core.network

import java.io.IOException

data class NetworkException(
    private val errorCode: Int,
    private val errorMessage: String = "Something went wrong"
) : IOException() {

    fun getDetailedInfo(): String {
        return errorMessage
    }

    companion object {
        const val ERROR_API = -1
        const val ERROR_NO_INTERNET = -2
        const val ERROR_HOST_NOT_FOUND = -3
        const val ERROR_TIMEOUT = -4
        const val ERROR_NETWORK_IO = -5
        const val ERROR_UNDEFINED = -6
    }
}