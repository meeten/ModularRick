package com.example.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.mapToScreenState
import com.example.domain.usecases.GetEpisodesUseCase
import com.example.domain.usecases.LoadNextEpisodesUseCase
import com.example.episodes.model.EpisodesScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    getEpisodesUseCase: GetEpisodesUseCase,
    private val loadNextEpisodesUseCase: LoadNextEpisodesUseCase
) : ViewModel() {

    private val isLoadNextDataFlow = MutableStateFlow(false)

    val uiState = getEpisodesUseCase()
        .mapToScreenState(
            onSuccess = {
                EpisodesScreenState.Episodes(it)
            },
            onError = {
                EpisodesScreenState.Error(
                    errorDescription = it.message ?: "Unknown error"
                )
            }
        ).combine(isLoadNextDataFlow) { state, isLoadNextData ->
            if (state is EpisodesScreenState.Episodes) {
                state.copy(
                    episodes = state.episodes,
                    isLoadNextData = isLoadNextData
                )
            } else {
                state
            }
        }.onStart { emit(EpisodesScreenState.Loading) }

    fun loadNextData() {
        viewModelScope.launch {
            isLoadNextDataFlow.value = true
            try {
                loadNextEpisodesUseCase()
            } finally {
                isLoadNextDataFlow.value = false
            }
        }
    }
}