package com.example.network

import com.example.getCharacterJsonResponse
import com.example.getCharactersJsonResponse
import com.example.getEpisodeJsonResponse
import com.example.getEpisodesJsonResponse
import kotlinx.coroutines.runBlocking
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: ApiService

    @BeforeEach
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Test
    fun `getCharacters should return valid characters dto when server returns 200`() {
        runBlocking {
            val jsonResponse = getCharactersJsonResponse()
            setMockResponse(jsonResponse)

            val result = apiService.getCharacters()
            assertEquals(1, result.characters.size)
            assertEquals(
                "https://rickandmortyapi.com/api/character/?page=2",
                result.infoDto.nextPageUrl
            )
            assertEquals(1, result.characters[0].id)
            assertEquals("Rick Sanchez", result.characters[0].name)
            assertEquals("Alive", result.characters[0].status)
            assertEquals("", result.characters[0].type)
            assertEquals("Male", result.characters[0].gender)
            assertEquals(1, result.characters[0].id)
            assertEquals("Earth", result.characters[0].originDto.name)
            assertEquals("Earth", result.characters[0].locationDto.name)
            assertEquals(
                "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                result.characters[0].imageUrl
            )
            assertEquals(
                "https://rickandmortyapi.com/api/episode/1",
                result.characters[0].episode[0]
            )
        }

        val request = mockWebServer.takeRequest()
        assertEquals("/character", request.url.encodedPath)
    }

    @Test
    fun `getCharacter should return valid character dto when server returns 200`() {
        runBlocking {
            val jsonResponse = getCharacterJsonResponse()
            setMockResponse(jsonResponse)

            val result = apiService.getCharacter(2)
            assertEquals(2, result.id)
            assertEquals("Morty Smith", result.name)
            assertEquals("Alive", result.status)
            assertEquals("Human", result.species)
            assertEquals("", result.type)
            assertEquals("Male", result.gender)
            assertEquals("Earth", result.originDto.name)
            assertEquals("Earth", result.locationDto.name)
            assertEquals(
                "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
                result.imageUrl
            )
            assertEquals(
                "https://rickandmortyapi.com/api/episode/1",
                result.episode[0]
            )

            val request = mockWebServer.takeRequest()
            assertEquals("/character/2", request.url.encodedPath)
        }
    }

    @Test
    fun `getEpisodeById should return valid episode dto when server returns 200`() {
        runBlocking {
            val jsonResponse = getEpisodeJsonResponse()
            setMockResponse(jsonResponse)

            val result = apiService.getEpisodeById("28")
            assertEquals(28, result.id)
            assertEquals("The Ricklantis Mixup", result.name)
            assertEquals("September 10, 2017", result.airDate)
            assertEquals("S03E07", result.episode)
            assertEquals(
                "https://rickandmortyapi.com/api/character/1",
                result.characters[0]
            )

            val request = mockWebServer.takeRequest()
            assertEquals("/episode/28", request.url.encodedPath)
        }
    }

    @Test
    fun `getEpisodesByIds should return valid episode dto list when server return 200`() {
        runBlocking {
            val jsonResponse = getEpisodesJsonResponse()
            setMockResponse(jsonResponse)

            val result = apiService.getEpisodesByIds("10,28")
            assertEquals(2, result.size)
            assertTrue(result.isNotEmpty())

            //первый EpisodeDto
            assertEquals(10, result[0].id)
            assertEquals(
                "Close Rick-counters of the Rick Kind",
                result[0].name
            )
            assertEquals("April 7, 2014", result[0].airDate)
            assertEquals("S01E10", result[0].episode)
            assertEquals(
                "https://rickandmortyapi.com/api/character/1",
                result[0].characters[0]
            )

            //второй EpisodeDto
            assertEquals(28, result[1].id)
            assertEquals("The Ricklantis Mixup", result[1].name)
            assertEquals("September 10, 2017", result[1].airDate)
            assertEquals("S03E07", result[1].episode)
            assertEquals(
                "https://rickandmortyapi.com/api/character/1",
                result[1].characters[0]
            )

            val request = mockWebServer.takeRequest()
            assertEquals("/episode/10,28", request.url.encodedPath)
        }
    }

    private fun setMockResponse(body: String, code: Int = 200) {
        mockWebServer.enqueue(
            MockResponse(body = body, code = code)
        )
    }
}