package com.example.home.model

import com.example.model.Character

sealed class HomeScreenState {

    object Loading : HomeScreenState()

    data class Characters(
        val characters: List<Character>
    ) : HomeScreenState()

    data class Error(
        val description: String
    ) : HomeScreenState()
}