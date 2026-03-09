package com.example.episodes.model

import com.example.model.Episode

internal sealed class EpisodesScreenState {

    object Initial : EpisodesScreenState()

    object Loading : EpisodesScreenState()

    data class Episodes(
        val episodes: List<Episode>
    ) : EpisodesScreenState()

    data class Error(val errorDescription: String) : EpisodesScreenState()
}