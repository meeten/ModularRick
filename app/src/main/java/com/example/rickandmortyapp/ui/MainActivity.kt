package com.example.rickandmortyapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.character.CharacterDetailScreen
import com.example.designsystem.theme.RickAndMortyAppTheme
import com.example.episodes.EpisodesScreen
import com.example.home.HomeScreen
import com.example.rickandmortyapp.navigation.AppNavGraph
import com.example.rickandmortyapp.navigation.rememberNavigationState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navigationState = rememberNavigationState()
            RickAndMortyAppTheme {
                AppNavGraph(
                    navController = navigationState.navController,
                    homeScreenContent = {
                        HomeScreen { characterId ->
                            navigationState.navigateToDetailCharacter(characterId)
                        }
                    },
                    detailCharacterScreenContent = { characterId ->
                        CharacterDetailScreen(
                            characterId = characterId,
                            onClickBack = {
                                navigationState.navController.popBackStack()
                            }) { character ->
                            navigationState.navigateToEpisodesScreen(character)
                        }
                    },
                    episodesScreenContent = { character ->
                        EpisodesScreen(character = character, onClickBack = {
                            navigationState.navController.popBackStack()
                        })
                    }
                )
            }
        }
    }
}