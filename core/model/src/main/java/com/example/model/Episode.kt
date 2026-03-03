package com.example.model

data class Episode(
    val id: Int,
    val name: String,
    val airDate: String,
    val seasonNumber: Int,
    val episodeNumber: Int,
    val characters: List<String>
)