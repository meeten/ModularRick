package com.example.model

import com.google.gson.annotations.SerializedName

data class InfoDto(
    @SerializedName("pages") val pages: Int,
    @SerializedName("next") val nextPageUrl: String?
)
