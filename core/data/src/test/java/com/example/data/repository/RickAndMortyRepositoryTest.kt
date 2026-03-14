package com.example.data.repository

import com.example.data.createCharacter
import com.example.data.createCharacterDto
import com.example.data.createEpisode
import com.example.data.createEpisodeDto
import com.example.data.createEpisodes
import com.example.data.createEpisodesDto
import com.example.data.mapper.RickAndMortyMapper
import com.example.model.OperationResult
import com.example.network.ApiService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RickAndMortyRepositoryTest {

    private lateinit var apiServiceMock: ApiService
    private lateinit var mapperMock: RickAndMortyMapper
    private lateinit var repositoryMock: RickAndMortyRepositoryImpl

    @BeforeEach
    fun setUp() {
        apiServiceMock = mockk<ApiService>()
        mapperMock = mockk<RickAndMortyMapper>()

        repositoryMock = RickAndMortyRepositoryImpl(apiServiceMock, mapperMock)
    }

    @Test
    fun `getCharacter should return a character when the network call is successful`() = runTest {
        val characterId = 1
        val dto = createCharacterDto(id = characterId)
        val model = createCharacter(id = characterId)

        coEvery { apiServiceMock.getCharacter(characterId) } returns dto
        every { mapperMock.mapResponseToCharacter(dto) } returns model

        repositoryMock.getCharacter(characterId).collect {
            assertTrue(it is OperationResult.Success)
            assertEquals(model, (it as OperationResult.Success).data)
        }
    }

    @Test
    fun `getCharacter should get data from the network when the cache is empty otherwise from the cache`() =
        runTest {

            val characterId = 1
            val dto = createCharacterDto(id = characterId)
            val model = createCharacter(id = characterId)

            coEvery { apiServiceMock.getCharacter(characterId) } returns dto
            every { mapperMock.mapResponseToCharacter(dto) } returns model

            repositoryMock.getCharacter(characterId).collect { } // первый вызов(network)
            repositoryMock.getCharacter(characterId).collect { } // второй вызов(кеш)

            coVerify(exactly = 1) { apiServiceMock.getCharacter(characterId) }
        }

    @Test
    fun `getCharacter should return failure when network returns error`() = runTest {
        val exception = RuntimeException("Network Error")

        coEvery { apiServiceMock.getCharacter(any()) } throws exception

        repositoryMock.getCharacter(1).collect {
            assertTrue(it is OperationResult.Failure)
            assertEquals(exception, (it as OperationResult.Failure).throwable)
        }
    }

    @Test
    fun `getEpisodes should call getEpisodeById when ids size is 1`() =
        runTest {
            val ids = listOf("1")
            val dto = createEpisodeDto()

            coEvery { apiServiceMock.getEpisodeById(ids[0]) } returns dto
            every { mapperMock.mapEpisodeDtoToEpisode(dto) } returns createEpisode()

            repositoryMock.getEpisodesByIds(ids).collect { }
            coVerify(exactly = 1) { apiServiceMock.getEpisodeById(ids[0]) }
        }

    @Test
    fun `getEpisodes should call getEpisodesByIds when ids size larger than 1`() = runTest {
        val ids = (0 until 10).toList().map { it.toString() }
        val idsString = ids.joinToString(",")
        val dto = createEpisodesDto()

        coEvery { apiServiceMock.getEpisodesByIds(idsString) } returns dto
        every { mapperMock.mapResponseToEpisodes(dto) } returns createEpisodes()

        repositoryMock.getEpisodesByIds(ids).collect { }
        coVerify(exactly = 1) { apiServiceMock.getEpisodesByIds(idsString) }
    }

    @Test
    fun `getEpisodes don't owe anything when ids is empty`() = runTest {
        val ids = emptyList<String>()
        val idsString = ids.joinToString(",")

        repositoryMock.getEpisodesByIds(ids).collect { }
        coVerify(exactly = 0) { apiServiceMock.getEpisodesByIds(idsString) }
    }

    @Test
    fun `getEpisodes should return model when network call is successful`() = runTest {
        val ids = (0 until 10).toList().map { it.toString() }
        val idsString = ids.joinToString(",")
        val dto = createEpisodesDto()
        val model = createEpisodes()

        coEvery { apiServiceMock.getEpisodesByIds(idsString) } returns dto
        every { mapperMock.mapResponseToEpisodes(dto) } returns model

        repositoryMock.getEpisodesByIds(ids).collect {
            assertTrue(it is OperationResult.Success)
            assertEquals(model, (it as OperationResult.Success).data)
        }
    }

    @Test
    fun `getEpisodes should return failure when network returns error`() = runTest {
        val exception = RuntimeException("Network Error")

        coEvery { apiServiceMock.getEpisodesByIds(any()) } throws exception

        repositoryMock.getEpisodesByIds(emptyList()).collect {
            assertTrue(it is OperationResult.Failure)
            assertEquals(exception, (it as OperationResult.Failure).throwable)
        }
    }

    @Test
    fun `getCharacter should re-call api method when network returns error`() = runTest {
        val id = 1
        val dto = createCharacterDto()
        val model = createCharacter()

        val exception = RuntimeException("Network Error")
        coEvery { apiServiceMock.getCharacter(1) } throws exception andThenThrows exception andThen dto
        every { mapperMock.mapResponseToCharacter(dto) } returns model

        repositoryMock.getCharacter(id).collect { }

        coVerify(exactly = 3) { apiServiceMock.getCharacter(1) }
    }

    @Test
    fun `getEpisodes should re-call api method when network returns error`() = runTest {
        val ids = (0 until 10).toList().map { it.toString() }
        val idsString = ids.joinToString(",")
        val dto = createEpisodesDto()
        val model = createEpisodes()

        val exception = RuntimeException("Network Error")
        coEvery { apiServiceMock.getEpisodesByIds(idsString) } throws exception andThenThrows exception andThen dto
        every { mapperMock.mapResponseToEpisodes(dto) } returns model

        repositoryMock.getEpisodesByIds(ids).collect { }

        coVerify(exactly = 3) { apiServiceMock.getEpisodesByIds(idsString) }
    }
}