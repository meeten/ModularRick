package com.example.rickandmortyapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.model.Character
import com.google.gson.Gson

@Composable
fun AppNavGraph(
    navController: NavHostController,
    homeScreenContent: @Composable () -> Unit,
    detailCharacterScreenContent: @Composable (Int) -> Unit,
    episodesScreenContent: @Composable (Character) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(route = Screen.HomeScreen.route) {
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
            route = Screen.EpisodesScreen.route,
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