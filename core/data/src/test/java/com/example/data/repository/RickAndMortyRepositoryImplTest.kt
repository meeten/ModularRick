package com.example.data.repository

import com.example.data.createCharacter
import com.example.data.createCharacterDto
import com.example.data.mapper.RickAndMortyMapper
import com.example.network.ApiService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
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
    fun `The getCharacter method should get data from the network when the cache is empty otherwise from the cache`() =
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
}