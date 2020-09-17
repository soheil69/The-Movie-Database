package com.example.core.exception

class TimeoutException(
    cause: Throwable? = null
) : Exception("Poor Internet Connection", cause)