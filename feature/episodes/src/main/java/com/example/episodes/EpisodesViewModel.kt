package com.example.episodes

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.common.mapToScreenState
import com.example.domain.usecases.GetEpisodesUseCase
import com.example.episodes.extension.asScreenState
import com.example.episodes.model.EpisodesScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
internal class EpisodesViewModel @Inject constructor(
    private val getEpisodesUseCase: GetEpisodesUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val uiState = savedStateHandle
        .getStateFlow<List<String>?>(KEY_URLS, null)
        .filterNotNull()
        .flatMapLatest { urls ->
            getEpisodesUseCase(urls)
                .mapToScreenState(
                    onSuccess = { episodes ->
                        episodes.asScreenState()
                    },
                    onError = { throwable ->
                        EpisodesScreenState.Error(
                            errorDescription = throwable.message ?: "Unknown error"
                        )
                    }
                )
                .onStart {
                    emit(EpisodesScreenState.Loading)
                }
        }

    fun setUrls(urls: List<String>) {
        savedStateHandle[KEY_URLS] = urls
    }
}

private const val KEY_URLS = "urls"