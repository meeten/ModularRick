package com.example.rickandmortyapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.character.CharacterDetailScreen
import com.example.designsystem.theme.RickAndMortyAppTheme
import com.example.episodes.EpisodesScreen
import com.example.home.HomeScreen
import com.example.rickandmortyapp.navigation.NavGraphApplication
import com.example.rickandmortyapp.navigation.rememberNavigationState
import com.example.rickandmortyapp.ui.bottombar.AppNavigationBottomBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navigationState = rememberNavigationState()
            RickAndMortyAppTheme {
                Scaffold(
                    bottomBar = {
                        AppNavigationBottomBar(navigationState = navigationState)
                    }
                ) {
                    Column(
                        modifier = Modifier.padding(
                            bottom = it.calculateBottomPadding()
                        )
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
                                    onClickBack = {
                                        navigationState.navController.popBackStack()
                                    }) { character ->
                                    navigationState.navigateToEpisodesScreen(character)
                                }
                            },
                            characterEpisodesScreenContent = { character ->
                                EpisodesScreen(character = character, onClickBack = {
                                    navigationState.navController.popBackStack()
                                })
                            },
                            allEpisodesScreenContent = {
                                MockContentScreen(title = "Episodes")
                            },
                            searchScreenContent = {
                                MockContentScreen(title = "Search")
                            }
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun MockContentScreen(
    title: String
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = title,
            fontSize = 32.sp,
        )
    }
}