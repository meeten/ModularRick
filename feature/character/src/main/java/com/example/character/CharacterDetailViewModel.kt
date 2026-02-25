package com.example.character

import androidx.lifecycle.ViewModel
import com.example.character.extension.asScreenState
import com.example.character.models.CharacterDetailScreenState
import com.example.common.mapToScreenState
import com.example.data.repository.RickAndMortyRepositoryImpl
import com.example.domain.usecases.GetCharacterUseCase
import kotlinx.coroutines.flow.onStart

internal class CharacterDetailViewModel(
    id: Int
) : ViewModel() {

    //TODO: inject
    private val repository = RickAndMortyRepositoryImpl
    private val getCharacterUseCase = GetCharacterUseCase(repository)

    val uiState = getCharacterUseCase(id)
        .mapToScreenState(
            onSuccess = { character ->
                character.asScreenState()
            },
            onError = { throwable ->
                CharacterDetailScreenState.Error(
                    errorDescription = throwable.message ?: ""
                )
            }
        )
        .onStart {
            emit(CharacterDetailScreenState.Loading)
        }
}