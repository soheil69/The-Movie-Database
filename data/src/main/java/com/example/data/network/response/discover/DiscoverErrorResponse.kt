package com.example.data.network.response.discover

import com.google.gson.annotations.SerializedName

internal data class DiscoverErrorResponse(

    @field:SerializedName("status_message")
    val statusMessage: String,

    @field:SerializedName("status_code")
    val statusCode: Int
)
