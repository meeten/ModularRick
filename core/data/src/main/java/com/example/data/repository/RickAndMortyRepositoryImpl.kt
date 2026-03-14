package com.example.data.repository

import com.example.data.mapper.RickAndMortyMapper
import com.example.domain.repository.RickAndMortyRepository
import com.example.model.Character
import com.example.model.Episode
import com.example.model.OperationResult
import com.example.network.ApiService
import kotlinx.coroutines.delay
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
        if (ids.isEmpty()) return@flow
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
        const val RETRY_TIMEOUT_MILLS = 3000L
    }
}