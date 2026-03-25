package com.example.character_episodes.model

import com.example.model.Episode

internal sealed class CharacterEpisodesScreenState {

    object Initial : CharacterEpisodesScreenState()

    object Loading : CharacterEpisodesScreenState()

    data class CharacterEpisodes(
        val episodes: List<Episode>
    ) : CharacterEpisodesScreenState()

    data class Error(val errorDescription: String) : CharacterEpisodesScreenState()
}