package com.example.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.mapToScreenState
import com.example.domain.usecases.GetCharactersUseCase
import com.example.domain.usecases.LoadNextCharactersUseCase
import com.example.home.model.HomeScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getCharactersUseCase: GetCharactersUseCase,
    private val loadNextCharactersUseCase: LoadNextCharactersUseCase

) : ViewModel() {

    private val isLoadNextDataFlow = MutableStateFlow(false)
    val uiState = getCharactersUseCase()
        .mapToScreenState(
            onSuccess = { characters ->
                if (characters.isEmpty()) HomeScreenState.Loading
                else HomeScreenState.Characters(characters = characters)
            },
            onError = { throwable ->
                HomeScreenState.Error(description = throwable.message ?: "Unknown error")
            }
        )
        .combine(isLoadNextDataFlow) { state, isLoadNextDataFlow ->
            if (state is HomeScreenState.Characters) {
                state.copy(
                    characters = state.characters,
                    isLoadNextData = isLoadNextDataFlow
                )
            } else {
                state
            }
        }
        .onStart { emit(HomeScreenState.Loading) }

    fun loadNextData() {
        viewModelScope.launch {
            isLoadNextDataFlow.value = true

            try {
                // даем небольшую фору UI, чтобы он успел показать лоадер
                // и не спамим API слишком часто
                delay(500)
                loadNextCharactersUseCase()
            } finally {
                isLoadNextDataFlow.value = false
            }
        }
    }
}