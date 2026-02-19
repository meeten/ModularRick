package com.example.model

data class Character(
    val id: Int,
    val gender: String,
    val name: String,
    val species: String,
    val status: CharacterStatus,
    val type: String,
    val imageUrl: String,
    val location: String,
    val origin: String,
    val episode: List<String>,
    val episodeCount: Int
)