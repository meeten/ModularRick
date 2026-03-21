package com.example.rickandmortyapp.navigation

import android.net.Uri
import com.example.model.Character
import com.google.gson.Gson

sealed class Screen(val route: String) {

    object Home : Screen(HOME_ROUTE)

    object Characters : Screen(CHARACTERS_ROUTE)

    object DetailCharacterScreen : Screen(DETAIL_CHARACTER_ROUTE) {
        private const val DETAIL_CHARACTER_ROUTE_WITH_ARGS = "detail_character"
        fun createRouteWithArgs(characterId: Int): String {
            return "$DETAIL_CHARACTER_ROUTE_WITH_ARGS/$characterId"
        }
    }

    object CharacterEpisodesScreen : Screen(CHARACTER_EPISODES_ROUTE) {
        private const val EPISODES_ROUTE_WITH_ARGS = "character_episodes"
        fun createRouteWithArgs(character: Character): String {
            val jsonCharacter = Gson().toJson(character)
            return "$EPISODES_ROUTE_WITH_ARGS/${jsonCharacter.encode()}"
        }
    }

    object AllEpisodesScreen : Screen(ALL_EPISODES_ROUTE)

    object Search : Screen(SEARCH_ROUTE)

    companion object {
        const val KEY_CHARACTER_ID = "character_id"
        const val KEY_CHARACTER = "character"

        private const val HOME_ROUTE = "home"
        private const val CHARACTERS_ROUTE = "characters"
        private const val DETAIL_CHARACTER_ROUTE = "detail_character/{$KEY_CHARACTER_ID}"
        private const val CHARACTER_EPISODES_ROUTE = "character_episodes/{$KEY_CHARACTER}"
        private const val ALL_EPISODES_ROUTE = "all_episodes"
        private const val SEARCH_ROUTE = "search"
    }

    fun String.encode(): String {
        return Uri.encode(this)
    }
}