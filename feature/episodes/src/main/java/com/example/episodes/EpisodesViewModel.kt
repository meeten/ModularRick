package com.example.episodes

import androidx.lifecycle.ViewModel
import com.example.common.mapToScreenState
import com.example.data.repository.RickAndMortyRepositoryImpl
import com.example.domain.usecases.GetEpisodesUseCase
import com.example.episodes.extension.asScreenState
import com.example.episodes.model.EpisodesScreenState
import kotlinx.coroutines.flow.onStart

class EpisodesViewModel(
    urls: List<String>
) : ViewModel() {

    //TODO: inject
    private val repository = RickAndMortyRepositoryImpl
    private val getEpisodesUseCase = GetEpisodesUseCase(repository)

    val uiState = getEpisodesUseCase(urls)
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