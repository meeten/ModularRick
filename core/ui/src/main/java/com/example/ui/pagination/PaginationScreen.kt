package com.example.ui.pagination

import androidx.compose.runtime.Composable
import com.example.ui.loading.Loading
import com.example.ui.pagination.model.PaginatingStateScreen

@Composable
fun <T> PaginationScreen(
    uiState: PaginatingStateScreen<T>,
    handlerError: (Throwable) -> Unit,
    content: @Composable (PaginatingStateScreen.Data<T>) -> Unit
) {
    when (uiState) {
        PaginatingStateScreen.Loading -> {
            Loading()
        }

        is PaginatingStateScreen.Data -> {
            content(uiState)
        }

        is PaginatingStateScreen.Error -> {
            handlerError(uiState.throwable)
        }
    }
}