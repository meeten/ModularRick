package com.example.episodes.model

import com.example.model.Episode

sealed class EpisodesScreenState {

    object Loading : EpisodesScreenState()

    data class Episodes(
        val episodes: List<Episode>,
        val isLoadNextData: Boolean = false
    ) : EpisodesScreenState()

    data class Error(
        val errorDescription: String
    ) : EpisodesScreenState()
}