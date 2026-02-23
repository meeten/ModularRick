package com.example.rickandmortyapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.example.character.CharacterDetailScreen
import com.example.designsystem.theme.RickAndMortyAppTheme
import com.example.rickandmortyapp.navigation.AppNavGraph
import com.example.rickandmortyapp.navigation.rememberNavigationState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navigationState = rememberNavigationState()
            RickAndMortyAppTheme {
                AppNavGraph(
                    navController = navigationState.navController,
                    detailCharacterScreenContent = {
                        CharacterDetailScreen(characterId = 235) {
                            navigationState.navigateToEpisodesScreen(it)
                        }
                    },
                    episodesScreenContent = {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Character id: $it", fontSize = 24.sp)
                        }
                    }
                )
            }
        }
    }
}