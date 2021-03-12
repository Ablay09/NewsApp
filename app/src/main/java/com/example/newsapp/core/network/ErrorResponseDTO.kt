package com.example.newsapp.core.network

data class ErrorResponseDTO(
    val status: String,
    val code: String,
    val message: String
)