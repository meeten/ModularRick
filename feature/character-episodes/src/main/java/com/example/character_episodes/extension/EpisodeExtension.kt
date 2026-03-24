package com.example.character_episodes.extension

import com.example.character_episodes.model.CharacterEpisodesScreenState
import com.example.model.Episode

internal fun List<Episode>?.asScreenState(): CharacterEpisodesScreenState {
    return this?.let { episodes ->
        CharacterEpisodesScreenState.CharacterEpisodes(
            episodes = episodes,
        )
    } ?: CharacterEpisodesScreenState.Initial
}