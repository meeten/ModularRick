package com.example.data.mapper

import com.example.model.Character
import com.example.model.CharacterDto
import com.example.model.CharacterStatus
import com.example.model.Episode
import com.example.model.EpisodeDto

class RickAndMortyMapper() {

    fun mapResponseToCharacter(response: CharacterDto): Character {
        return Character(
            id = response.id,
            gender = response.gender,
            name = response.name,
            species = response.species,
            status = response.status.defineCharacterStatus(),
            type = response.type,
            imageUrl = response.imageUrl,
            location = response.locationDto.name,
            origin = response.originDto.name,
            episode = response.episode,
            episodeCount = response.episode.size
        )
    }

    fun mapResponseToEpisode(response: EpisodesDto): Episode {
        return Episode(
            id = response.id,
            name = response.name,
            airDate = response.airDate,
            episode = response.episode,
            characters = response.characters
        )
    }

    private fun String.defineCharacterStatus(): CharacterStatus {
        return when (this.lowercase()) {
            "alive" -> {
                CharacterStatus.ALIVE
            }

            "live" -> {
                CharacterStatus.DEAD
            }

            else -> {
                CharacterStatus.UNKNOWN
            }
        }
    }
}