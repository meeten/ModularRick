package com.example.episodes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.episodes.ui.EpisodesContent
import com.example.ui.extension.sortedGroupEpisodesBySeasonNumber
import com.example.ui.extension.uniqueCharactersCountPerSeason
import com.example.ui.pagination.PaginationScreen
import com.example.ui.pagination.model.PaginatingStateScreen
import com.example.ui.topbar.TopAppBarCustom

@Composable
fun EpisodesScreen(
    modifier: Modifier = Modifier,
    viewModel: EpisodesViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState
        .collectAsState(initial = PaginatingStateScreen.Loading).value

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBarCustom(title = R.string.all_episodes)
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            PaginationScreen(
                uiState = uiState,
                handlerError = {},
                content = { data ->
                    val groupEpisodes = data.items.sortedGroupEpisodesBySeasonNumber()
                    EpisodesContent(
                        groupEpisodes = groupEpisodes,
                        uniqueCharactersCounts = groupEpisodes.uniqueCharactersCountPerSeason(),
                        isLoadNextData = data.isLoadNextData,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        viewModel.loadNextEpisodes()
                    }
                })
        }
    }
}