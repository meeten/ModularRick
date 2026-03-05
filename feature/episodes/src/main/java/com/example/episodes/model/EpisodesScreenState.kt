package com.example.episodes.model

import com.example.model.Episode

sealed class EpisodesScreenState {

    object Initial : EpisodesScreenState()

    object Loading : EpisodesScreenState()

    data class Episodes(
        val episodes: List<Episode>
    ) : EpisodesScreenState()

    data class Error(val errorDescription: String) : EpisodesScreenState()
}