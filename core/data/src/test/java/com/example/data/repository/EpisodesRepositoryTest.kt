package com.example.data.repository

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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class EpisodesRepositoryTest {

    private lateinit var apiServiceMock: ApiService
    private lateinit var mapperMock: RickAndMortyMapper
    private lateinit var coroutineScopeTest: TestScope
    private lateinit var repository: EpisodesRepositoryImpl

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setUp() {
        apiServiceMock = mockk<ApiService>()
        mapperMock = mockk<RickAndMortyMapper>()
        coroutineScopeTest = TestScope(UnconfinedTestDispatcher())

        repository = EpisodesRepositoryImpl(
            apiService = apiServiceMock,
            mapper = mapperMock,
            coroutineScope = coroutineScopeTest
        )
    }

    @Test
    fun `getEpisodes should call getEpisodeById when ids size is 1`() =
        runTest {
            val ids = listOf("1")
            val dto = createEpisodeDto()

            coEvery { apiServiceMock.getEpisodeById(ids[0]) } returns dto
            every { mapperMock.mapEpisodeDtoToEpisode(dto) } returns createEpisode()

            repository.getEpisodesByIds(ids).collect { }
            coVerify(exactly = 1) { apiServiceMock.getEpisodeById(ids[0]) }
        }

    @Test
    fun `getEpisodes should call getEpisodesByIds when ids size larger than 1`() = runTest {
        val ids = (0 until 10).toList().map { it.toString() }
        val idsString = ids.joinToString(",")
        val dto = createEpisodesDto()

        coEvery { apiServiceMock.getEpisodesByIds(idsString) } returns dto
        every { mapperMock.mapResponseToEpisodes(dto) } returns createEpisodes()

        repository.getEpisodesByIds(ids).collect { }
        coVerify(exactly = 1) { apiServiceMock.getEpisodesByIds(idsString) }
    }

    @Test
    fun `getEpisodes should return model when network call is successful`() = runTest {
        val ids = (0 until 10).toList().map { it.toString() }
        val idsString = ids.joinToString(",")
        val dto = createEpisodesDto()
        val model = createEpisodes()

        coEvery { apiServiceMock.getEpisodesByIds(idsString) } returns dto
        every { mapperMock.mapResponseToEpisodes(dto) } returns model

        repository.getEpisodesByIds(ids).collect {
            assertTrue(it is OperationResult.Success)
            assertEquals(model, (it as OperationResult.Success).data)
        }
    }

    @Test
    fun `getEpisodes should return failure when network returns error`() = runTest {
        val exception = RuntimeException("Network Error")

        coEvery { apiServiceMock.getEpisodesByIds(any()) } throws exception

        repository.getEpisodesByIds(emptyList()).collect {
            assertTrue(it is OperationResult.Failure)
            assertEquals(exception, (it as OperationResult.Failure).throwable)
        }
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

        repository.getEpisodesByIds(ids).collect { }

        coVerify(exactly = 3) { apiServiceMock.getEpisodesByIds(idsString) }
    }
}