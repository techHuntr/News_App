package com.ewind.newsapi.data.remote.model

import com.google.gson.annotations.SerializedName

data class Error(
    @SerializedName("status") val status: String?,
    @SerializedName("code") val code: String?,
    @SerializedName("message") val message: String?
)
