package com.example.episodes

import com.example.common.exception.GlobalExceptionManager
import com.example.domain.usecases.GetEpisodesUseCase
import com.example.domain.usecases.LoadNextEpisodesUseCase
import com.example.model.Episode
import com.example.ui.pagination.PaginatingViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    globalExceptionManager: GlobalExceptionManager,
    getEpisodesUseCase: GetEpisodesUseCase,
    private val loadNextEpisodesUseCase: LoadNextEpisodesUseCase,
) : PaginatingViewModel<List<Episode>>(globalExceptionManager) {

    val uiState = createPaginatingUiState {
        getEpisodesUseCase()
    }

    fun loadNextEpisodes() {
        loadNextData {
            loadNextEpisodesUseCase()
        }
    }
}