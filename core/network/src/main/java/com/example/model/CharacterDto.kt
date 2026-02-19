package com.example.model

import com.google.gson.annotations.SerializedName

data class CharacterDto(
    @SerializedName("gender")
    val gender: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("species")
    val species: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("image")
    val imageUrl: String,
    @SerializedName("episode")
    val episode: List<String>,
    @SerializedName("location")
    val locationDto: LocationDto,
    @SerializedName("origin")
    val originDto: OriginDto
)