package com.example.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.home.model.HomeScreenState
import com.example.home.ui.CharactersContent
import com.example.ui.Loading

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onClickCharacter: (Int) -> Unit,
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsState(initial = HomeScreenState.Loading)

    Scaffold(modifier = modifier) {
        Column(modifier = Modifier.padding(it)) {
            when (val currentState = uiState.value) {
                is HomeScreenState.Loading -> {
                    Loading()
                }

                is HomeScreenState.Characters -> {
                    CharactersContent(
                        characters = currentState.characters,
                        isLoadNextData = currentState.isLoadNextData,
                        loadNextData = { viewModel.loadNextData() },
                        onClickCharacter = onClickCharacter
                    )
                }

                is HomeScreenState.Error -> {
                    Text(text = currentState.description)
                }
            }
        }
    }
}