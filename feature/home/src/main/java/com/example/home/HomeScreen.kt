package com.example.home

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ui.character.CharactersContent
import com.example.ui.exception.model.ExceptionScreenState
import com.example.ui.pagination.PaginationScreen
import com.example.ui.pagination.model.PaginatingStateScreen
import com.example.ui.topbar.TopAppBarCustom

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onClickCharacter: (Int) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsState(
        initial = PaginatingStateScreen.Loading
    ).value
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.errorEvent.collect { exceptionState ->
            when (exceptionState) {
                is ExceptionScreenState.ShowError -> {
                    Toast.makeText(
                        context,
                        exceptionState.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = { TopAppBarCustom(title = R.string.all_characters) }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            PaginationScreen(
                uiState,
                handlerError = { throwable ->
                    viewModel.sendErrorEvent(throwable)
                },
                content = { data ->
                    CharactersContent(
                        characters = data.items,
                        isLoadNextData = data.isLoadNextData,
                        loadNextData = { viewModel.loadNextCharacters() },
                        onClickCharacter = onClickCharacter
                    )
                }
            )
        }
    }
}