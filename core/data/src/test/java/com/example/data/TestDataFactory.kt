package com.example.data

import com.example.model.CharacterDto
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