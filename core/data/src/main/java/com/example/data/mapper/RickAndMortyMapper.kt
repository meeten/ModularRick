package com.example.data.mapper

import com.example.model.Character
import com.example.model.CharacterDto
import com.example.model.CharacterStatus
import com.example.model.Episode
import com.example.model.EpisodeDto
import jakarta.inject.Inject

class RickAndMortyMapper @Inject constructor() {

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

    fun mapResponseToEpisodes(response: List<EpisodeDto>): List<Episode> {
        val result = mutableListOf<Episode>()
        response.forEach { episodeDto ->
            val episode = mapEpisodeDtoToEpisode(episodeDto)
            result.add(episode)
        }
        return result
    }

    fun mapEpisodeDtoToEpisode(episodeDto: EpisodeDto): Episode {
        val formattedEpisode = episodeDto.episode.formatEpisode()
        return Episode(
            id = episodeDto.id,
            name = episodeDto.name,
            airDate = episodeDto.airDate,
            seasonNumber = formattedEpisode.getOrDefault(SEASON_KEY, 0),
            episodeNumber = formattedEpisode.getOrDefault(EPISODE_KEY, 0),
            characters = episodeDto.characters
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

    //format before: S02E01
    //format after: {season: 2, episode: 1}
    private fun String.formatEpisode(): Map<String, Int> {
        val indexE = this.indexOf('E')
        val season = this.substring(1, indexE).toInt()
        val episode = this.substring(indexE + 1).toInt()
        return mapOf(
            Pair(SEASON_KEY, season),
            Pair(EPISODE_KEY, episode)
        )
    }

    private companion object {
        const val SEASON_KEY = "season"
        const val EPISODE_KEY = "episode"
    }
}