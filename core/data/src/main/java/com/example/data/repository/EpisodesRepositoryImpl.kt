package com.example.data.repository

import com.example.data.base.BasePagingRepository
import com.example.data.base.PageResult
import com.example.data.mapper.RickAndMortyMapper
import com.example.domain.repository.EpisodesRepository
import com.example.model.Episode
import com.example.model.OperationResult
import com.example.network.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EpisodesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: RickAndMortyMapper,
    coroutineScope: CoroutineScope
) : BasePagingRepository<Episode>(coroutineScope), EpisodesRepository {

    override suspend fun fetchUrl(url: String?): PageResult<Episode> {
        val response = if (url == null) {
            apiService.getEpisodes()
        } else {
            apiService.getEpisodes(fullUrl = url)
        }
        val episodes = mapper.mapResponseToEpisodes(response.episodes)
        episodes.forEach { episode ->
            dataCache[episode.id] = episode
        }
        return PageResult(
            items = episodes,
            nextUrl = response.infoDto.nextPageUrl
        )
    }

    override val episodesData: StateFlow<OperationResult<List<Episode>>> = data

    override suspend fun loadNextEpisodes() = loadNextData()
    override fun getEpisodesByIds(ids: List<String>) = flow {
        emit(fetchEpisodes(ids))
    }.asOperationResultFlow()

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
}