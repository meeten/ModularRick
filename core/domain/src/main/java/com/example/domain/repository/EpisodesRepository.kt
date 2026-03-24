package com.example.domain.repository

import com.example.model.Episode
import com.example.model.OperationResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface EpisodesRepository {

    val episodesData: StateFlow<OperationResult<List<Episode>>>

    suspend fun loadNextEpisodes()

    fun getEpisodesByIds(ids: List<String>):
            Flow<OperationResult<List<Episode>>>
}