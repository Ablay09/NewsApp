package com.example.newsapp.core.network

import com.google.gson.Gson
import com.google.gson.JsonParseException
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ErrorMappingInterceptor(
    private val networkHandler: NetworkHandler,
    private val gson: Gson
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return try {
            if (!networkHandler.isConnected) {
                throw NetworkException(NetworkException.ERROR_NO_INTERNET, "No internet connection")
            }
            val request = chain.request()
            val response = chain.proceed(request)
            if (response.isSuccessful) {
                response
            } else {
                throw mapErrorBodyToException(response)
            }
        } catch (throwable: Throwable) {
            throw throwable.mapNetworkException()
        }
    }

    private fun mapErrorBodyToException(response: Response): Throwable {
        val bodyString = response.body?.string()

        val errorResponseDTO: ErrorResponseDTO? = extractError(bodyString)
        return if (errorResponseDTO != null) {
            NetworkException(
                NetworkException.ERROR_API,
                errorResponseDTO.message
            )
        } else {
            NetworkException(NetworkException.ERROR_API)
        }
    }

    private fun extractError(bodyString: String?): ErrorResponseDTO? {
        return try {
            gson.fromJson(bodyString, ErrorResponseDTO::class.java)
        } catch (e: JsonParseException) {
            null
        }
    }

    private fun Throwable.mapNetworkException(): Throwable {
        return when (this) {
            is NetworkException -> this
            is SocketTimeoutException ->
                NetworkException(NetworkException.ERROR_TIMEOUT, "Limit exceeded")
            is UnknownHostException ->
                NetworkException(NetworkException.ERROR_HOST_NOT_FOUND, "Host not found")
            is IOException ->
                NetworkException(NetworkException.ERROR_NETWORK_IO)
            else ->
                NetworkException(NetworkException.ERROR_UNDEFINED)
        }
    }
}