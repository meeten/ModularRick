package com.example.rickandmortyapp.navigation

sealed class Screen(val route: String) {

    object DetailCharacterScreen : Screen(DETAIL_CHARACTER_ROUTE)

    object EpisodesScreen : Screen(EPISODES_ROUTE) {

        private const val EPISODES_ROUTE_WITH_ARGS = "episodes"
        fun createRouteWithArgs(characterId: Int): String {
            return "$EPISODES_ROUTE_WITH_ARGS/${characterId}"
        }
    }

    companion object {

        const val KEY_CHARACTER_ID = "character_id"

        private const val DETAIL_CHARACTER_ROUTE = "detail_character"
        private const val EPISODES_ROUTE = "episodes/{$KEY_CHARACTER_ID}"
    }
}