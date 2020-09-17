package com.example.core.model

data class BaseResponse<T>(
    val httpStatusCode: Int,
    val statusCode: String?,
    val statusMessage: String?,
    val data: DataResponse<T>?
)
