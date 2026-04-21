package com.example.home

import com.example.domain.usecases.GetCharactersUseCase
import com.example.domain.usecases.LoadNextCharactersUseCase
import com.example.ui.pagination.PaginatingViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getCharactersUseCase: GetCharactersUseCase,
    private val loadNextCharactersUseCase: LoadNextCharactersUseCase
) : PaginatingViewModel<List<Character>>() {

    val uiState = createPaginatingUiState {
        getCharactersUseCase()
    }

    fun loadNextCharacters() {
        loadNextData {
            loadNextCharactersUseCase()
        }
    }
}