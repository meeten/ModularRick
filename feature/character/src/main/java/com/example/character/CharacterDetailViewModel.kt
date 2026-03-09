package com.example.character

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.character.extension.asScreenState
import com.example.character.models.CharacterDetailScreenState
import com.example.common.mapToScreenState
import com.example.domain.usecases.GetCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
internal class CharacterDetailViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val uiState = savedStateHandle
        .getStateFlow<Int?>(KEY_CHARACTER_ID, null)
        .filterNotNull()
        .filter { it > 0 }
        .flatMapLatest { id ->
            getCharacterUseCase(id)
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


    fun setCharacterId(id: Int) {
        savedStateHandle[KEY_CHARACTER_ID] = id
    }
}

private const val KEY_CHARACTER_ID = "character_id"