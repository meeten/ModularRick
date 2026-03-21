package com.example.rickandmortyapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.model.Character
import com.google.gson.Gson

fun NavGraphBuilder.charactersNavGraph(
    homeScreenContent: @Composable () -> Unit,
    detailCharacterScreenContent: @Composable (Int) -> Unit,
    episodesScreenContent: @Composable (Character) -> Unit
) {
    navigation(
        startDestination = Screen.Characters.route,
        route = Screen.Home.route
    ) {
        composable(route = Screen.Characters.route) {
            homeScreenContent()
        }

        composable(
            route = Screen.DetailCharacterScreen.route,
            arguments = listOf(navArgument(name = Screen.KEY_CHARACTER_ID) {
                type = NavType.IntType
            })
        ) {
            val characterId = it.arguments?.getInt(Screen.KEY_CHARACTER_ID) ?: -1
            detailCharacterScreenContent(characterId)
        }

        composable(
            route = Screen.CharacterEpisodesScreen.route,
            arguments = listOf(navArgument(name = Screen.KEY_CHARACTER) {
                type = NavType.StringType
            })
        ) {
            val character = Gson().fromJson(
                it.arguments?.getString(Screen.KEY_CHARACTER),
                Character::class.java
            )
            episodesScreenContent(character)
        }
    }

}