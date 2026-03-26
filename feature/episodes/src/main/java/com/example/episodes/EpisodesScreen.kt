package com.example.episodes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.episodes.model.EpisodesScreenState
import com.example.episodes.ui.EpisodesContent
import com.example.ui.extension.sortedGroupEpisodesBySeasonNumber
import com.example.ui.extension.uniqueCharactersCountPerSeason
import com.example.ui.loading.Loading
import com.example.ui.topbar.TopAppBarCustom

@Composable
fun EpisodesScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: EpisodesViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsState(initial = EpisodesScreenState.Loading)

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBarCustom(title = R.string.all_episodes)
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            when (val currentState = uiState.value) {
                EpisodesScreenState.Loading -> {
                    Loading()
                }

                is EpisodesScreenState.Episodes -> {
                    val groupEpisodes = currentState.episodes.sortedGroupEpisodesBySeasonNumber()
                    EpisodesContent(
                        groupEpisodes = groupEpisodes,
                        uniqueCharactersCounts = groupEpisodes.uniqueCharactersCountPerSeason(),
                        isLoadNextData = currentState.isLoadNextData,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        viewModel.loadNextData()
                    }
                }

                is EpisodesScreenState.Error -> {
                    Text(text = currentState.errorDescription)
                }
            }
        }
    }
}