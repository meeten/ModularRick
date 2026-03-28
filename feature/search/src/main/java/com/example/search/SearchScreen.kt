package com.example.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.search.model.SearchScreenState
import com.example.search.ui.SearchBarCustom
import com.example.ui.character.CharactersContent
import com.example.ui.loading.Loading
import com.example.ui.topbar.TopAppBarCustom

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    val uiState = searchViewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = { TopAppBarCustom(title = R.string.search) }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
        ) {
            SearchBarCustom(modifier = Modifier.padding(16.dp)) { query ->
                searchViewModel.onChangeQuery(query)
            }

            when (val currentState = uiState.value) {

                SearchScreenState.Initial -> {}

                SearchScreenState.Loading -> {
                    Loading()
                }

                is SearchScreenState.Result -> {
                    CharactersContent(
                        characters = currentState.results,
                        isLoadNextData = false,
                        loadNextData = {},
                        onClickCharacter = {}
                    )
                }

                is SearchScreenState.Error -> {
                    Text(text = currentState.errorDescription)
                }
            }
        }
    }
}