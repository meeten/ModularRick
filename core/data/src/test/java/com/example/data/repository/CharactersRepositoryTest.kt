package com.example.data.repository

import app.cash.turbine.test
import com.example.data.createCharacter
import com.example.data.createCharacterDto
import com.example.data.createCharacters
import com.example.data.createCharactersResponseDto
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

class CharactersRepositoryTest {

    private lateinit var apiServiceMock: ApiService
    private lateinit var mapperMock: RickAndMortyMapper
    private lateinit var coroutineScopeTest: TestScope
    private lateinit var repository: CharactersRepositoryImpl

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setUp() {
        apiServiceMock = mockk<ApiService>()
        mapperMock = mockk<RickAndMortyMapper>()
        coroutineScopeTest = TestScope(UnconfinedTestDispatcher())

        repository = CharactersRepositoryImpl(
            apiService = apiServiceMock,
            mapper = mapperMock,
            coroutineScope = coroutineScopeTest
        )
    }

    @Test
    fun `characterData should initial with empty list`() = runTest {
        val charactersData = repository.charactersData.value
        assertTrue(charactersData is OperationResult.Success && charactersData.data.isEmpty())
    }

    @Test
    fun `charactersData should fetch first page on initialization`() = runTest {
        val charactersDto = createCharactersResponseDto(
            nextPageUrl = "info/page=2"
        )
        val characters = createCharacters()

        coEvery { apiServiceMock.getCharacters() } returns charactersDto
        every { mapperMock.mapResponseToCharacters(charactersDto) } returns characters

        repository.loadNextCharacters()

        repository.charactersData.test {
            val result = awaitItem()
            assertTrue(result is OperationResult.Success)
            assertEquals(characters, (result as OperationResult.Success).data)
            coVerify(exactly = 1) {
                apiServiceMock.getCharacters()
            }
        }
    }

    @Test
    fun `charactersData should fetch next page when loadNextCharacters is called`() = runTest {
        val page1Url = "info/page=2"
        val dto1 = createCharactersResponseDto(nextPageUrl = page1Url)
        val chars1 = listOf(createCharacter(id = 1))

        val page2Url = "info/page=3"
        val dto2 = createCharactersResponseDto(nextPageUrl = page2Url)
        val chars2 = listOf(createCharacter(id = 2))

        coEvery { apiServiceMock.getCharacters() } returns dto1
        coEvery { apiServiceMock.getCharacters(fullUrl = page1Url) } returns dto2

        every { mapperMock.mapResponseToCharacters(dto1) } returns chars1
        every { mapperMock.mapResponseToCharacters(dto2) } returns chars2

        repository.charactersData.test {
            val firstResult = awaitItem()
            assertTrue(firstResult is OperationResult.Success)
            assertEquals(chars1, (firstResult as OperationResult.Success).data)

            repository.loadNextCharacters()

            var secondResult = awaitItem()
            assertTrue(secondResult is OperationResult.Success)
            secondResult = secondResult as OperationResult.Success
            assertEquals(2, secondResult.data.size)
            assertTrue(secondResult.data.containsAll(chars1 + chars2))

            coVerify(exactly = 1) { apiServiceMock.getCharacters() }
            coVerify(exactly = 1) { apiServiceMock.getCharacters(fullUrl = page1Url) }
        }
    }

    @Test
    fun `getCharacter should return a character when the network call is successful`() = runTest {
        val characterId = 1
        val dto = createCharacterDto(id = characterId)
        val model = createCharacter(id = characterId)

        coEvery { apiServiceMock.getCharacter(characterId) } returns dto
        every { mapperMock.mapResponseToCharacter(dto) } returns model

        repository.getCharacter(characterId).collect {
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

            repository.getCharacter(characterId).collect { } // первый вызов(network)
            repository.getCharacter(characterId).collect { } // второй вызов(кеш)

            coVerify(exactly = 1) { apiServiceMock.getCharacter(characterId) }
        }

    @Test
    fun `getCharacter should return failure when network returns error`() = runTest {
        val exception = RuntimeException("Network Error")

        coEvery { apiServiceMock.getCharacter(any()) } throws exception

        repository.getCharacter(1).collect {
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

        repository.getCharacter(id).collect { }

        coVerify(exactly = 3) { apiServiceMock.getCharacter(1) }
    }
}