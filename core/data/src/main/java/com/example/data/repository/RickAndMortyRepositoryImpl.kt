package com.example.data.repository

import com.example.data.mapper.RickAndMortyMapper
import com.example.domain.repository.RickAndMortyRepository
import com.example.model.Character
import com.example.model.Episode
import com.example.model.OperationResult
import com.example.network.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RickAndMortyRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: RickAndMortyMapper,
    coroutineScope: CoroutineScope
) : RickAndMortyRepository {

    private val nextDataNeededEvents = MutableSharedFlow<Unit>(replay = 1)

    private val charactersCache = mutableMapOf<Int, Character>()
    private var nextFrom: String? = null
    private val loadedCharacters = flow<List<Character>> {
        nextDataNeededEvents.emit(Unit)
        nextDataNeededEvents.collect {
            val startFrom = nextFrom
            if (startFrom == null && charactersCache.isNotEmpty()) {
                emit(charactersCache.values.toList())
                return@collect
            }

            val response = if (startFrom == null) {
                apiService.getCharacters()
            } else {
                apiService.getCharacters(fullUrl = startFrom)
            }

            nextFrom = response.infoDto.nextPageUrl
            val result = mapper.mapResponseToCharacters(response)
            result.forEach { character ->
                charactersCache[character.id] = character
            }
            emit(charactersCache.values.toList())
        }
    }

    override val charactersData = loadedCharacters
        .map { OperationResult.Success(it) as OperationResult<List<Character>> }
        .retryWhen { cause, attempt ->
            if ((cause as? retrofit2.HttpException)?.code() == TOO_MANY_REQUEST_CODE) {
                delay(RETRY_TIMEOUT_MILLS)
                return@retryWhen true
            }

            val shouldRetry = attempt < MAX_ATTEMPTS
            if (shouldRetry) {
                delay(RETRY_TIMEOUT_MILLS)
            }
            shouldRetry
        }
        .catch {
            emit(OperationResult.Failure(it))
        }.stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = OperationResult.Success(emptyList())
        )

    override suspend fun loadNextCharacters() {
        nextDataNeededEvents.emit(Unit)
    }

    override fun getCharacter(id: Int) = flow {
        val character = charactersCache.getOrPut(key = id) {
            mapper.mapResponseToCharacter(apiService.getCharacter(id))
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

    companion object {
        const val TOO_MANY_REQUEST_CODE = 429
        const val MAX_ATTEMPTS = 3
        const val RETRY_TIMEOUT_MILLS = 3000L
    }
}