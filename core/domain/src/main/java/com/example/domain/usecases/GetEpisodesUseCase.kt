package com.example.domain.usecases

import com.example.domain.repository.RickAndMortyRepository
import com.example.model.Episode
import com.example.model.OperationResult
import kotlinx.coroutines.flow.StateFlow

class GetEpisodesUseCase(
    private val rickAndMortyRepository: RickAndMortyRepository
) {

    operator fun invoke(urls: List<String>): StateFlow<OperationResult<List<Episode>>> {
        return rickAndMortyRepository.getEpisodesByUrls(urls)
    }
}