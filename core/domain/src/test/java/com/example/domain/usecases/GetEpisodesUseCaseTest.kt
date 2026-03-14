package com.example.domain.usecases

import com.example.domain.repository.RickAndMortyRepository
import com.example.model.Episode
import com.example.model.OperationResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class GetEpisodesUseCaseTest {

    private lateinit var repositoryMock: RickAndMortyRepository
    private lateinit var getEpisodesUseCaseMock: GetEpisodesUseCase

    @BeforeEach
    fun setUp() {
        repositoryMock = mockk<RickAndMortyRepository>()
        getEpisodesUseCaseMock = GetEpisodesUseCase(repositoryMock)
    }

    @Test
    fun `getEpisodesUSeCases should transform urls to ids and fetch episodes`() =
        runTest {
            val urls = listOf(
                "https://rickandmortyapi.com/api/character/1",
                "https://rickandmortyapi.com/api/character/2"
            )
            val ids = listOf("1", "2")
            val episodesMock = mockk<List<Episode>>()

            coEvery { repositoryMock.getEpisodesByIds(ids) } returns flowOf(
                OperationResult.Success(
                    episodesMock
                )
            )

            getEpisodesUseCaseMock(urls).collect {
                assertTrue(it is OperationResult.Success)
                assertEquals(episodesMock, (it as OperationResult.Success).data)
            }

            coVerify(exactly = 1) { repositoryMock.getEpisodesByIds(ids) }
        }

    @ParameterizedTest
    @MethodSource("provideCollections")
    fun `getEpisodesUseCase should return empty flow and doesn't call getEpisodesByIds when urls is invalid`(
        inputUrls: List<String>
    ) =
        runTest {
            val result = getEpisodesUseCaseMock(inputUrls).toList()
            assertTrue(
                result.isEmpty(),
                "Flow should be empty for input: $inputUrls"
            )
            coVerify(exactly = 0) { repositoryMock.getEpisodesByIds(any()) }
        }

    companion object {
        @JvmStatic
        fun provideCollections(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(emptyList<String>()),
                Arguments.of(listOf("https://rickandmortyapi.com/api/character/")),
                Arguments.of(listOf("something", "some string")),
                Arguments.of(listOf("/", "1", "\n", "", " ")),
            )
        }
    }
}