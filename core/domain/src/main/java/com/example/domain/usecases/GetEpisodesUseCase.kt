package com.example.domain.usecases

import com.example.domain.repository.EpisodesRepository
import com.example.model.Episode
import com.example.model.OperationResult
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetEpisodesUseCase @Inject constructor(
    private val repository: EpisodesRepository
) {

    operator fun invoke(): StateFlow<OperationResult<List<Episode>>> {
        return repository.episodesData
    }
}