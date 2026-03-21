package com.example.rickandmortyapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.model.Character

@Composable
fun NavGraphApplication(
    navController: NavHostController,
    homeScreenContent: @Composable () -> Unit,
    detailCharacterScreenContent: @Composable (Int) -> Unit,
    characterEpisodesScreenContent: @Composable (Character) -> Unit,
    allEpisodesScreenContent: @Composable () -> Unit,
    searchScreenContent: @Composable () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        charactersNavGraph(
            homeScreenContent = homeScreenContent,
            detailCharacterScreenContent = detailCharacterScreenContent,
            episodesScreenContent = characterEpisodesScreenContent
        )

        composable(Screen.AllEpisodesScreen.route) {
            allEpisodesScreenContent()
        }

        composable(Screen.Search.route) {
            searchScreenContent()
        }
    }
}