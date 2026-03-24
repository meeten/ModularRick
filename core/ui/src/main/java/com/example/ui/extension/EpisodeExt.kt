package com.example.ui.extension

import com.example.model.Episode

fun List<Episode>.sortedGroupEpisodesBySeasonNumber(): Map<Int, List<Episode>> {
    return this.sortedBy { it.seasonNumber }
        .groupBy { it.seasonNumber }
}

fun Map<Int, List<Episode>>.uniqueCharactersEpisodes(): IntArray {
    val result = IntArray(this.keys.size + 1)
    for (i in 0 until result.size) {
        val uniqueCharacters = mutableSetOf<String>()
        this[i]?.forEach { episode ->
            episode.characters.forEach {
                uniqueCharacters.add(it)
            }
        }
        result[i] += uniqueCharacters.size
    }
    return result
}