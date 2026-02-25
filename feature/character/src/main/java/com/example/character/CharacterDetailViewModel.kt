package com.example.character

import androidx.lifecycle.ViewModel
import com.example.character.extension.asScreenState
import com.example.character.models.CharacterDetailScreenState
import com.example.data.repository.RickAndMortyRepositoryImpl
import com.example.domain.usecases.GetCharacterUseCase
import com.example.model.OperationResult
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

internal class CharacterDetailViewModel(
    id: Int
) : ViewModel() {

    //TODO: inject
    private val repository = RickAndMortyRepositoryImpl
    private val getCharacterUseCase = GetCharacterUseCase(repository)

    val uiState = getCharacterUseCase(id)
        .map { operationResult ->
            when (operationResult) {
                is OperationResult.Success -> {
                    operationResult.data.asScreenState()
                }

                is OperationResult.Failure -> {
                    CharacterDetailScreenState.Error(
                        errorDescription = operationResult.throwable.message ?: ""
                    )
                }
            }
        }
        .onStart {
            emit(CharacterDetailScreenState.Loading)
        }
}