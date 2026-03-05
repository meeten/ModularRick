package com.example.episodes.extension

import com.example.episodes.model.EpisodesScreenState
import com.example.model.Episode

internal fun Map<Int, List<Episode>>?.asScreenState(): EpisodesScreenState {
    return this?.let { episodes ->
        EpisodesScreenState.Episodes(
            episodes = episodes,
        )
    } ?: EpisodesScreenState.Initial
}