package com.example.newsapp.core.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi


class NetworkHandler(private val context: Context) {
    val isConnected: Boolean
        @RequiresApi(Build.VERSION_CODES.M)
        get() {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                    as? ConnectivityManager ?: return false
            val networkInfo = connectivityManager.activeNetwork
            val networkCapabilities = connectivityManager.getNetworkCapabilities(networkInfo)
                ?: return false
            return with(networkCapabilities) {
                when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> true
                    else -> false
                }
            }
        }
}