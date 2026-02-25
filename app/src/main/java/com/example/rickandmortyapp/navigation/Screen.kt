package com.example.rickandmortyapp.navigation

import android.net.Uri
import com.example.model.Character
import com.google.gson.Gson

sealed class Screen(val route: String) {

    object DetailCharacterScreen : Screen(DETAIL_CHARACTER_ROUTE)

    object EpisodesScreen : Screen(EPISODES_ROUTE) {

        private const val EPISODES_ROUTE_WITH_ARGS = "episodes"
        fun createRouteWithArgs(character: Character): String {
            val characterJson = Gson().toJson(character)
            return "$EPISODES_ROUTE_WITH_ARGS/${characterJson.encode()}"
        }
    }

    companion object {

        const val KEY_CHARACTER_ID = "character_id"

        private const val DETAIL_CHARACTER_ROUTE = "detail_character"
        private const val EPISODES_ROUTE = "episodes/{$KEY_CHARACTER_ID}"
    }

    fun String.encode(): String {
        return Uri.encode(this)
    }
}