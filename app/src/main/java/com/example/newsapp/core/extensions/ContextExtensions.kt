package com.example.newsapp.core.extensions

import android.content.Context
import android.widget.Toast
import com.example.newsapp.core.network.NetworkException

fun Context.getErrorMessage(throwable: Throwable): String {
    return if (throwable is NetworkException) {
        throwable.getDetailedInfo()
    } else {
        throwable.message.toString()
    }
}

fun Context.showToast(message: String?, long: Boolean = true) {
    val duration = when (long) {
        true -> Toast.LENGTH_LONG
        else -> Toast.LENGTH_SHORT
    }
    Toast.makeText(this, message, duration).show()
}