package com.example.episodes.extension

import com.example.episodes.model.EpisodesScreenState
import com.example.model.Episode

internal fun List<Episode>?.asScreenState(): EpisodesScreenState {
    return this?.let { episodes ->
        EpisodesScreenState.Episodes(
            episodes = episodes,
        )
    } ?: EpisodesScreenState.Initial
}

internal fun List<Episode>.sortedGroupEpisodesBySeasonNumber(): Map<Int, List<Episode>> {
    return this.sortedBy { it.seasonNumber }
        .groupBy { it.seasonNumber }
}