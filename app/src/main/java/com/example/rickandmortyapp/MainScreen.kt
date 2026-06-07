package com.example.rickandmortyapp

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.character.CharacterDetailScreen
import com.example.character_episodes.CharacterEpisodesScreen
import com.example.episodes.EpisodesScreen
import com.example.home.HomeScreen
import com.example.rickandmortyapp.navigation.NavGraphApplication
import com.example.rickandmortyapp.navigation.NavigationState
import com.example.search.SearchScreen

@Composable
fun MainScreen(
    navigationState: NavigationState,
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        NavGraphApplication(
            navController = navigationState.navController,
            homeScreenContent = {
                HomeScreen { characterId ->
                    navigationState.navigateToDetailCharacter(characterId)
                }
            },
            detailCharacterScreenContent = { characterId ->
                CharacterDetailScreen(
                    characterId = characterId,
                    onClickBack = onClickBack,
                    onViewAllEpisodesClick = { character ->
                        navigationState.navigateToEpisodesScreen(character)
                    }
                )
            },

            characterEpisodesScreenContent = { character ->
                CharacterEpisodesScreen(
                    character = character,
                    onClickBack = onClickBack
                )
            },
            allEpisodesScreenContent = {
                EpisodesScreen()
            },
            searchScreenContent = {
                SearchScreen()
            }
        )
    }
}