package com.example.home

import androidx.lifecycle.ViewModel
import com.example.common.mapToScreenState
import com.example.domain.usecases.GetCharactersUseCase
import com.example.home.model.HomeScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    val uiState = getCharactersUseCase()
        .mapToScreenState(
            onSuccess = { characters ->
                if (characters.isEmpty()) HomeScreenState.Loading
                else HomeScreenState.Characters(characters = characters)
            },
            onError = { throwable ->
                HomeScreenState.Error(description = throwable.message ?: "Unknown error")
            }
        ).onStart {
            emit(HomeScreenState.Loading)
        }
}