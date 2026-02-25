package com.example.episodes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.episodes.model.EpisodesScreenState
import com.example.model.Character
import com.example.ui.Loading

@Composable
fun EpisodesScreen(
    character: Character,
    modifier: Modifier = Modifier
) {
    val viewModel: EpisodesViewModel =
        viewModel(factory = EpisodesViewModelFactory(character.episode))
    val uiState = viewModel.uiState.collectAsState(
        initial = EpisodesScreenState.Initial
    )

    when (val currentState = uiState.value) {
        is EpisodesScreenState.Initial -> {}
        is EpisodesScreenState.Loading -> {
            Loading()
        }

        is EpisodesScreenState.Episodes -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Character id: ${character.id}", fontSize = 24.sp)
            }
        }

        is EpisodesScreenState.Error -> {
            Text(text = currentState.errorDescription)
        }
    }
}