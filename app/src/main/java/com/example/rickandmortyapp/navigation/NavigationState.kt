package com.example.rickandmortyapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.model.Character

class NavigationState(val navController: NavHostController) {

    fun navigateTo(route: String) {
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }

            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToDetailCharacter(characterId: Int) {
        navController.navigate(
            Screen.DetailCharacterScreen.createRouteWithArgs(characterId)
        )
    }

    fun navigateToEpisodesScreen(character: Character) {
        navController.navigate(
            Screen.CharacterEpisodesScreen.createRouteWithArgs(character)
        )
    }
}

@Composable
fun rememberNavigationState(
    navController: NavHostController = rememberNavController()
): NavigationState {
    return remember { NavigationState(navController) }
}
