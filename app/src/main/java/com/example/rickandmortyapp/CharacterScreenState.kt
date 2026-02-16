package com.example.rickandmortyapp

sealed class CharacterScreenState {

    object Initial : CharacterScreenState()

    object Loading : CharacterScreenState()

    data class Character(val character: com.example.model.Character) : CharacterScreenState()
}