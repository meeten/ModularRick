package com.example.character

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.character.models.CharacterDetailScreenState
import com.example.character.ui.CharacterDetailContent
import com.example.model.Character
import com.example.ui.Loading

@Composable
fun CharacterDetailScreen(
    characterId: Int,
    modifier: Modifier = Modifier,
    onViewAllEpisodesClick: (Character) -> Unit,
) {
    val viewModel: CharacterDetailViewModel = viewModel(
        factory = CharacterDetailViewModelFactory(characterId)
    )
    val uiState = viewModel.uiState.collectAsState(
        CharacterDetailScreenState.Initial
    )

    Scaffold(modifier = modifier) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            when (val currentState = uiState.value) {
                CharacterDetailScreenState.Initial -> {}

                CharacterDetailScreenState.Loading -> {
                    Loading()
                }

                is CharacterDetailScreenState.CharacterDetail -> {
                    CharacterDetailContent(
                        status = currentState.character.status,
                        name = currentState.character.name,
                        imageUrl = currentState.character.imageUrl,
                        fieldsInfo = currentState.fieldsInfo,
                        modifier = Modifier.padding(8.dp),
                        onViewAllEpisodesClick = {
                            onViewAllEpisodesClick(currentState.character)
                        }
                    )
                }

                is CharacterDetailScreenState.Error -> {
                    Text(text = currentState.errorDescription)
                }
            }
        }
    }
}