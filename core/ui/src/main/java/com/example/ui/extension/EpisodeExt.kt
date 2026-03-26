package com.example.ui.extension

import com.example.model.Episode

fun List<Episode>.sortedGroupEpisodesBySeasonNumber(): Map<Int, List<Episode>> {
    return this.sortedBy { it.seasonNumber }
        .groupBy { it.seasonNumber }
}

fun Map<Int, List<Episode>>.uniqueCharactersCountPerSeason(): IntArray {
    val maxSeasonNumber = keys.maxOrNull() ?: return IntArray(0)

    return IntArray(maxSeasonNumber + 1) {
        this[it]
            ?.flatMap { episode -> episode.characters }
            ?.distinct()
            ?.size ?: 0
    }
}