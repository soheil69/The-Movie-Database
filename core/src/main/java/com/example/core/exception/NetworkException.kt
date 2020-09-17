package com.example.core.exception

import com.example.core.model.NetworkError

class NetworkException(
    networkError: NetworkError,
    cause: Throwable? = null
) : Exception(networkError.statusMessage, cause)