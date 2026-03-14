package com.example.data

import com.example.model.Character
import com.example.model.CharacterDto
import com.example.model.CharacterStatus
import com.example.model.Episode
import com.example.model.EpisodeDto
import com.example.model.LocationDto
import com.example.model.OriginDto

internal fun createCharacterDto(
    id: Int = 1,
    gender: String = "Gender",
    name: String = "Name",
    species: String = "Species",
    status: String = "alive",
    type: String = "Type",
    imageUrl: String = "ImageUrl",
    location: LocationDto = LocationDto("Location"),
    origin: OriginDto = OriginDto("Origin"),
    episode: List<String> = listOf("", "", ""),
) = CharacterDto(
    id = id,
    gender = gender,
    name = name,
    species = species,
    status = status,
    type = type,
    imageUrl = imageUrl,
    locationDto = location,
    originDto = origin,
    episode = episode
)

internal fun createEpisodeDto(
    id: Int = 1,
    name: String = "name",
    airDate: String = "date",
    episode: String = "S01E01",
    characters: List<String> = listOf("Character1", ",Character2", "Character3")
) = EpisodeDto(
    id = id,
    name = name,
    airDate = airDate,
    episode = episode,
    characters = characters
)

internal fun createEpisodesDto(): List<EpisodeDto> {
    val result = mutableListOf<EpisodeDto>().apply {
        repeat(10) {
            add(createEpisodeDto(id = it))
        }
    }
    return result
}

internal fun createCharacter(
    id: Int = 1,
    gender: String = "Gender",
    name: String = "Name",
    species: String = "Species",
    status: CharacterStatus = CharacterStatus.UNKNOWN,
    type: String = "Type",
    imageUrl: String = "ImageUrl",
    location: String = "Location",
    origin: String = "Origin",
    episode: List<String> = listOf("", "", ""),
    episodeCount: Int = 3
) = Character(
    id = id,
    gender = gender,
    name = name,
    species = species,
    status = status,
    type = type,
    imageUrl = imageUrl,
    location = location,
    origin = origin,
    episode = episode,
    episodeCount = episodeCount
)

internal fun createEpisode(
    id: Int = 1,
    name: String = "name",
    airDate: String = "date",
    episode: String = "S01E01",
    characters: List<String> = listOf("Character1", ",Character2", "Character3")
) = Episode(
    id = id,
    name = name,
    airDate = airDate,
    episodeNumber = 1,
    seasonNumber = 1,
    characters = characters
)

internal fun createEpisodes(): List<Episode> {
    val result = mutableListOf<Episode>().apply {
        repeat(10) {
            add(createEpisode(id = it))
        }
    }

    return result
}