package com.example.data.mapper

import com.example.data.createCharacterDto
import com.example.data.createCharactersDto
import com.example.data.createEpisodeDto
import com.example.data.createEpisodesDto
import com.example.model.CharacterStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class RickAndMortyMapperTest {

    private lateinit var mapper: RickAndMortyMapper

    @BeforeEach
    fun setUp() {
        mapper = RickAndMortyMapper()
    }

    @Test
    fun `mapResponseToCharacter should correctly map all DTO fields to Domain model`() {
        val characterDto = createCharacterDto()
        val character = mapper.mapResponseToCharacter(characterDto)

        assertEquals(characterDto.id, character.id)
        assertEquals(characterDto.gender, character.gender)
        assertEquals(characterDto.name, character.name)
        assertEquals(characterDto.species, character.species)
        assertEquals(characterDto.type, character.type)
        assertEquals(characterDto.imageUrl, character.imageUrl)
        assertEquals(characterDto.locationDto.name, character.location)
        assertEquals(characterDto.originDto.name, character.origin)
        assertEquals(characterDto.episode, character.episode)
        assertEquals(CharacterStatus.ALIVE, character.status)
    }

    @Test
    fun `mapResponseToCharacters should accurately map list of DTOs to domain characters`() {
        val charactersDto = createCharactersDto()
        val characters = mapper.mapResponseToCharacters(charactersDto)

        assertEquals(characters.size, charactersDto.characters.size)

        characters.forEachIndexed { index, character ->
            val characterDto = charactersDto.characters[index]
            assertEquals(characterDto.id, character.id)
            assertEquals(characterDto.gender, character.gender)
            assertEquals(characterDto.name, character.name)
            assertEquals(characterDto.species, character.species)
            assertEquals(characterDto.type, character.type)
            assertEquals(characterDto.imageUrl, character.imageUrl)
            assertEquals(characterDto.locationDto.name, character.location)
            assertEquals(characterDto.originDto.name, character.origin)
            assertEquals(characterDto.episode, character.episode)
            assertEquals(CharacterStatus.ALIVE, character.status)
        }
    }

    @Test
    fun `mapEpisodeDtoToEpisode should return correctly map all DTO fields to Domain model`() {
        val episodeDto = createEpisodeDto()
        val episode = mapper.mapEpisodeDtoToEpisode(episodeDto)

        assertEquals(episodeDto.name, episode.name)
        assertEquals(episodeDto.airDate, episode.airDate)
        assertEquals(episodeDto.characters, episode.characters)
        assertEquals(1, episode.seasonNumber)
        assertEquals(1, episode.episodeNumber)
    }

    @Test
    fun `mapResponseToEpisodes should accurately map list of DTOs to domain episodes`() {
        val episodesDto = createEpisodesDto()
        val episodes = mapper.mapResponseToEpisodes(episodesDto)

        assertTrue(episodesDto.size == episodes.size)

        episodes.forEachIndexed { index, episode ->
            val episodeDto = episodesDto[index]
            assertEquals(episodeDto.name, episode.name)
            assertEquals(episodeDto.airDate, episode.airDate)
            assertEquals(episodeDto.characters, episode.characters)
            assertEquals(1, episode.seasonNumber)
            assertEquals(1, episode.episodeNumber)
        }
    }

    @ParameterizedTest
    @CsvSource(
        "ALIVE, alive",
        "Alive, alive",
        "alive, alive",
        "DEAD, dead",
        "Dead, dead",
        "UNKNOWN, unknown",
        "Unknown, unknown",
        "unknown, unknown",
        "everything, unknown",
        "'', unknown"
    )
    fun `defineCharacterStatus should correctly map strings regardless of case `(
        input: String, expected: String,
    ) {
        // т.к метод приватный в маппере, проверяем через маппинг всего DTO
        val characterDto = createCharacterDto(status = input)
        val character = mapper.mapResponseToCharacter(characterDto)
        assertEquals(expected, character.status.type, "Failed for $input")
    }

    @ParameterizedTest
    @CsvSource(
        //строка, сезон, епизод
        "S01E01, 1, 1",
        "S02E100, 2, 100",
        "S03E1, 3, 1",
        "S10E01, 10, 1",
        "S4E2, 4, 2"
    )
    fun `String_formatEpisode should correctly parse season and episode`(
        input: String,
        season: Int,
        episode: Int
    ) {
        // т.к метод приватный в маппере, проверяем через маппинг всего DTO
        val episodeDto = createEpisodeDto(episode = input)
        val episodeModel = mapper.mapEpisodeDtoToEpisode(episodeDto)
        assertEquals(season, episodeModel.seasonNumber, "Failed for $input")
        assertEquals(episode, episodeModel.episodeNumber, "Failed for $input")
    }

    @Test
    fun `String_formatEpisode should throw NumberFormatException when string is malformatted`() {
        //за место цифр после S - буквы
        val episode1 = "SXXE01"
        val episodeDto1 = createEpisodeDto(episode = episode1)
        assertThrows<NumberFormatException> {
            mapper.mapEpisodeDtoToEpisode(episodeDto1)
        }

        //за место цифр после E - буквы
        val episode2 = "S100EXX"
        val episodeDto2 = createEpisodeDto(episode = episode2)
        assertThrows<NumberFormatException> {
            mapper.mapEpisodeDtoToEpisode(episodeDto2)
        }

        //после S сразу следует E
        val episode3 = "SE01"
        val episodeDto3 = createEpisodeDto(episode = episode3)
        assertThrows<NumberFormatException> {
            mapper.mapEpisodeDtoToEpisode(episodeDto3)
        }

        //после E ничего нет
        val episode4 = "S06E"
        val episodeDto4 = createEpisodeDto(episode = episode4)
        assertThrows<NumberFormatException> {
            mapper.mapEpisodeDtoToEpisode(episodeDto4)
        }
    }

    @Test
    fun `String_formatEpisode should throw IndexOutOfBoundsException when string is missing letters`() {
        //нет буквы S
        val episode1 = "E01"
        val episodeDto1 = createEpisodeDto(episode = episode1)
        assertThrows<IndexOutOfBoundsException> {
            mapper.mapEpisodeDtoToEpisode(episodeDto1)
        }

        //нет буквы E
        val episode2 = "S01"
        val episodeDto2 = createEpisodeDto(episode = episode2)
        assertThrows<IndexOutOfBoundsException> {
            mapper.mapEpisodeDtoToEpisode(episodeDto2)
        }

        //нет буквы S и E
        val episode3 = "0101"
        val episodeDto3 = createEpisodeDto(episode = episode3)
        assertThrows<IndexOutOfBoundsException> {
            mapper.mapEpisodeDtoToEpisode(episodeDto3)
        }
    }
}