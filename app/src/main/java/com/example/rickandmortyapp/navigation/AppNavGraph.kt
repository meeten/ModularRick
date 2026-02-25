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
    detailCharacterScreenContent: @Composable () -> Unit,
    episodesScreenContent: @Composable (Character) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.DetailCharacterScreen.route
    ) {
        composable(route = Screen.DetailCharacterScreen.route) {
            detailCharacterScreenContent()
        }

        composable(
            route = Screen.EpisodesScreen.route,
            arguments = listOf(navArgument(name = Screen.KEY_CHARACTER_ID) {
                type = NavType.StringType
            })
        ) {
            val character = Gson()
                .fromJson<Character>(
                    it.arguments?.getString(Screen.KEY_CHARACTER_ID),
                    Character::class.java
                )
            episodesScreenContent(character)
        }
    }
}