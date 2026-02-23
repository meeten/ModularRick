package com.example.rickandmortyapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun AppNavGraph(
    navController: NavHostController,
    detailCharacterScreenContent: @Composable () -> Unit,
    episodesScreenContent: @Composable (Int) -> Unit
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
                type = NavType.IntType
            })
        ) {
            val characterId = it.arguments?.getInt(Screen.KEY_CHARACTER_ID) ?: -1
            episodesScreenContent(characterId)
        }
    }
}