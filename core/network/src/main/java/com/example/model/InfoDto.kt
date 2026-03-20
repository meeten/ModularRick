package com.example.model

import com.google.gson.annotations.SerializedName

data class InfoDto(
    @SerializedName("next") val nextPageUrl: String?
)