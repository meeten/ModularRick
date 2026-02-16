package com.example.model

data class Character(
    val id: Int,
    val gender: String,
    val name: String,
    val species: String,
    val status: CharacterStatus,
    val type: String,
    val imageUrl: String
)