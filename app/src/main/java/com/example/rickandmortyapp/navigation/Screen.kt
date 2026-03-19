package com.example.rickandmortyapp.navigation

import android.net.Uri
import com.example.model.Character
import com.google.gson.Gson

sealed class Screen(val route: String) {

    object HomeScreen : Screen(HOME_ROUTE)

    object DetailCharacterScreen : Screen(DETAIL_CHARACTER_ROUTE) {
        private const val DETAIL_CHARACTER_ROUTE_WITH_ARGS = "detail_character"
        fun createRouteWithArgs(characterId: Int): String {
            return "$DETAIL_CHARACTER_ROUTE_WITH_ARGS/$characterId"
        }
    }

    object EpisodesScreen : Screen(EPISODES_ROUTE) {
        private const val EPISODES_ROUTE_WITH_ARGS = "episodes"
        fun createRouteWithArgs(character: Character): String {
            val jsonCharacter = Gson().toJson(character)
            return "$EPISODES_ROUTE_WITH_ARGS/${jsonCharacter.encode()}"
        }
    }

    companion object {
        const val KEY_CHARACTER_ID = "character_id"
        const val KEY_CHARACTER = "character"

        private const val HOME_ROUTE = "characters"
        private const val DETAIL_CHARACTER_ROUTE = "detail_character/{$KEY_CHARACTER_ID}"
        private const val EPISODES_ROUTE = "episodes/{$KEY_CHARACTER}"
    }

    fun String.encode(): String {
        return Uri.encode(this)
    }
}