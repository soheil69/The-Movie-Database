package com.example.core.exception

class ConnectivityException(
    cause: Throwable? = null
) : Exception("No Internet Connection", cause)