package com.example.rickandmortyapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class NavigationState(val navController: NavHostController) {

    fun navigateToEpisodesScreen(characterId: Int) {
        navController.navigate(
            Screen.EpisodesScreen.createRouteWithArgs(characterId)
        )
    }
}

@Composable
fun rememberNavigationState(
    navController: NavHostController = rememberNavController()
): NavigationState {
    return remember { NavigationState(navController) }
}
