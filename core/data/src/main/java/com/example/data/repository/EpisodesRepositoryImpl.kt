package com.example.data.repository

import com.example.data.mapper.RickAndMortyMapper
import com.example.domain.repository.EpisodesRepository
import com.example.model.Episode
import com.example.model.OperationResult
import com.example.network.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton

//TODO: нарушение DRY с CharactersRepository, вынести всю повторяющуюся логику в абстракцию
@Singleton
class EpisodesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: RickAndMortyMapper,
    coroutineScope: CoroutineScope
) : EpisodesRepository {

    private val nextDataNeededEvents = MutableSharedFlow<Unit>(replay = 1)

    private val episodesCache = mutableMapOf<Int, Episode>()
    private var nextFrom: String? = null
    private val loadedEpisodes = flow {
        nextDataNeededEvents.emit(Unit)
        nextDataNeededEvents.collect {
            val startFrom = nextFrom
            if (startFrom == null && episodesCache.isNotEmpty()) {
                emit(episodesCache.values.toList())
                return@collect
            }

            val response = if (startFrom == null) {
                apiService.getEpisodes()
            } else {
                apiService.getEpisodes(fullUrl = startFrom)
            }

            nextFrom = response.infoDto.nextPageUrl
            val result = mapper.mapResponseToEpisodes(response.episodes)
            result.forEach { episode ->
                episodesCache[episode.id] = episode
            }
            emit(episodesCache.values.toList())
        }
    }

    override val episodesData: StateFlow<OperationResult<List<Episode>>> = loadedEpisodes
        .map { OperationResult.Success(it) as OperationResult<List<Episode>> }
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
        .catch { emit(OperationResult.Failure(it)) }
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = OperationResult.Success(emptyList())
        )

    override suspend fun loadNextEpisodes() {
        nextDataNeededEvents.emit(Unit)
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