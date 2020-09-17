package com.example.core.model

data class NetworkError(
    val httpStatusCode: Int,
    val httpStatusMessage: String,
    val statusCode: Int,
    val statusMessage: String,
)