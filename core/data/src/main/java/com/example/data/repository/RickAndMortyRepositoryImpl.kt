package com.example.data.repository

import com.example.data.mapper.RickAndMortyMapper
import com.example.domain.repository.RickAndMortyRepository
import com.example.model.Character
import com.example.model.Episode
import com.example.model.OperationResult
import com.example.network.ApiService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retry
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RickAndMortyRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: RickAndMortyMapper
) : RickAndMortyRepository {

    private val nextDataNeededEvents = MutableSharedFlow<Unit>(replay = 1)

    private val _characters = mutableListOf<Character>()
    private val characters
        get() = _characters.toList()
    private var nextFrom: String? = null
    private val loadedCharacters = flow<List<Character>> {
        nextDataNeededEvents.emit(Unit)
        nextDataNeededEvents.collect {
            val startFrom = nextFrom
            if (startFrom == null && characters.isNotEmpty()) {
                emit(characters)
                return@collect
            }

            val response =
                if (startFrom == null) apiService.getCharacters()
                else apiService.getCharacters(
                    page = startFrom.urlParserToPageNumber()
                )
            nextFrom = response.infoDto.nextPageUrl

            _characters.addAll(
                mapper.mapResponseToCharacters(response)
            )
            emit(characters)
        }
    }

    override fun getCharacters(): Flow<OperationResult<List<Character>>> =
        loadedCharacters
            .map { OperationResult.Success(it) as OperationResult<List<Character>> }
            .retry(2) {
                RETRY_TIMEOUT_MILLS
                true
            }
            .catch {
                emit(OperationResult.Failure(it))
            }

    override suspend fun loadNextCharacters() {
        nextDataNeededEvents.emit(Unit)
    }

    private val _charactersCache = mutableMapOf<Int, Character>()
    override fun getCharacter(id: Int) = flow {
        val character = _charactersCache.getOrPut(key = id) {
            mapper
                .mapResponseToCharacter(apiService.getCharacter(id))
        }
        emit(character)
    }.map {
        OperationResult.Success(data = it) as OperationResult<Character>
    }.retry(2) {
        delay(RETRY_TIMEOUT_MILLS)
        true
    }.catch {
        emit(OperationResult.Failure(it))
    }

    override fun getEpisodesByIds(ids: List<String>) = flow {
        emit(fetchEpisodes(ids))
    }.map {
        OperationResult.Success(it) as OperationResult<List<Episode>>
    }.retry(2) {
        delay(RETRY_TIMEOUT_MILLS)
        true
    }.catch {
        emit(OperationResult.Failure(it))
    }

    private suspend fun fetchEpisodes(ids: List<String>): List<Episode> {
        return when (ids.size) {
            1 -> {
                val response = apiService.getEpisodeById(ids[0])
                val episode = mapper.mapEpisodeDtoToEpisode(response)
                listOf(episode)
            }

            else -> {
                val response = apiService.getEpisodesByIds(
                    ids.joinToString(",")
                )
                val episodes = mapper.mapResponseToEpisodes(response)
                episodes
            }
        }
    }

    private fun String.urlParserToPageNumber(): String {
        val pageNumber =
            this.substringAfterLast(
                "/",
                missingDelimiterValue = ""
            )
        return if (pageNumber.isNotEmpty() && pageNumber.all { it.isDigit() }) pageNumber else ""
    }

    companion object {
        const val RETRY_TIMEOUT_MILLS = 3000L
    }
}