package com.example.episodes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.episodes.extension.sortedGroupEpisodesBySeasonNumber
import com.example.episodes.model.EpisodesScreenState
import com.example.episodes.ui.EpisodesContent
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

    Scaffold(modifier = modifier) {
        Column(modifier = Modifier.padding(it)) {
            when (val currentState = uiState.value) {
                is EpisodesScreenState.Initial -> {}

                is EpisodesScreenState.Loading -> {
                    Loading()
                }

                is EpisodesScreenState.Episodes -> {
                    EpisodesContent(
                        characterName = character.name,
                        imageUrl = character.imageUrl,
                        groupedEpisodes = currentState.episodes.sortedGroupEpisodesBySeasonNumber(),
                        modifier = Modifier.padding(16.dp)
                    )
                }

                is EpisodesScreenState.Error -> {
                    Text(text = currentState.errorDescription)
                }
            }
        }
    }
}