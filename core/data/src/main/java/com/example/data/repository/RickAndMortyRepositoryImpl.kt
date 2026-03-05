package com.example.data.repository

import com.example.data.mapper.RickAndMortyMapper
import com.example.domain.repository.RickAndMortyRepository
import com.example.model.Character
import com.example.model.Episode
import com.example.model.OperationResult
import com.example.network.ApiFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn

object RickAndMortyRepositoryImpl : RickAndMortyRepository {

    //TODO: inject
    private val apiService = ApiFactory.apiService
    private val rickAndMortyMapper = RickAndMortyMapper()

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private val _charactersCache = mutableMapOf<Int, Character>()
    override fun getCharacter(id: Int) = flow {
        val character = _charactersCache[id] ?: rickAndMortyMapper
            .mapResponseToCharacter((apiService.getCharacter(id)))
        emit(character)
    }.map {
        OperationResult.Success(data = it) as OperationResult<Character>
    }.retry(2) {
        true
    }.catch {
        emit(OperationResult.Failure(it))
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = OperationResult.Success<Character>(null)
    )

    override fun getEpisodesByUrls(ids: List<String>) = flow {
        if (ids.size == 1) {
            val response = apiService.getEpisodeById(ids[0])
            val episode = rickAndMortyMapper.mapEpisodeDtoToEpisode(response)
            emit(listOf(episode))
        } else {
            val response = apiService.getEpisodesByIds(
                ids.joinToString(",")
            )
            val episodes = rickAndMortyMapper.mapResponseToEpisodes(response)
            emit(episodes)
        }
    }.map {
        OperationResult.Success(it) as OperationResult<List<Episode>>
    }.retry(2) {
        true
    }.catch {
        emit(OperationResult.Failure(it))
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = OperationResult.Success(emptyList())
    )
}