package com.example.character

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.character.models.CharacterDetailScreenState
import com.example.character.ui.CharacterItemContent
import com.example.ui.Loading

@Composable
fun CharacterDetailScreen(
    modifier: Modifier
) {
    val viewModel: CharacterDetailViewModel = viewModel(
        factory = CharacterDetailViewModelFactory(2)
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
                    Loading(modifier = Modifier)
                }

                is CharacterDetailScreenState.CharacterDetail -> {
                    CharacterItemContent(
                        character = currentState.character,
                        fieldsInfo = currentState.fieldsInfo,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}