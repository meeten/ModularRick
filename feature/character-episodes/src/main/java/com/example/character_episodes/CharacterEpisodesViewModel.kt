package com.example.character_episodes

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.extenstion.mapToScreenState
import com.example.domain.usecases.GetCharacterEpisodesUseCase
import com.example.character_episodes.extension.asScreenState
import com.example.character_episodes.model.CharacterEpisodesScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
internal class CharacterEpisodesViewModel @Inject constructor(
    private val getCharacterEpisodesUseCase: GetCharacterEpisodesUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val uiState = savedStateHandle
        .getStateFlow<List<String>?>(KEY_URLS, null)
        .filterNotNull()
        .flatMapLatest { urls ->
            getCharacterEpisodesUseCase(urls)
                .mapToScreenState(
                    onSuccess = { episodes ->
                        episodes.asScreenState()
                    },
                    onError = { throwable ->
                        CharacterEpisodesScreenState.Error(
                            errorDescription = throwable.message ?: "Unknown error"
                        )
                    }
                )
                .onStart {
                    emit(CharacterEpisodesScreenState.Loading)
                }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CharacterEpisodesScreenState.Initial
        )

    fun setUrls(urls: List<String>) {
        savedStateHandle[KEY_URLS] = urls
    }
}

private const val KEY_URLS = "urls"