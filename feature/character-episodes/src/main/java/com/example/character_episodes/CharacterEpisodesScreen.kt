package com.example.character_episodes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.character_episodes.model.CharacterEpisodesScreenState
import com.example.character_episodes.ui.EpisodesContent
import com.example.model.Character
import com.example.ui.extension.sortedGroupEpisodesBySeasonNumber
import com.example.ui.loading.Loading
import com.example.ui.topbar.NavigationIconBack
import com.example.ui.topbar.TopAppBarCustom

@Composable
fun CharacterEpisodesScreen(
    character: Character,
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit,
) {
    val viewModel: CharacterEpisodesViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        viewModel.setUrls(character.episode)
    }
    val uiState = viewModel.uiState.collectAsState(
        initial = CharacterEpisodesScreenState.Initial
    )

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBarCustom(
                title = R.string.characters_episodes,
                navigationIcon = { NavigationIconBack(onClickIcon = onClickBack) }
            )
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            when (val currentState = uiState.value) {
                is CharacterEpisodesScreenState.Initial -> {}

                is CharacterEpisodesScreenState.Loading -> {
                    Loading()
                }

                is CharacterEpisodesScreenState.CharacterEpisodes -> {
                    EpisodesContent(
                        characterName = character.name,
                        imageUrl = character.imageUrl,
                        groupedEpisodes = currentState.episodes.sortedGroupEpisodesBySeasonNumber(),
                        modifier = Modifier.padding(16.dp)
                    )
                }

                is CharacterEpisodesScreenState.Error -> {
                    Text(text = currentState.errorDescription)
                }
            }
        }
    }
}