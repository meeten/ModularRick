package com.example.domain.usecases

import com.example.domain.repository.EpisodesRepository
import javax.inject.Inject

class LoadNextEpisodesUseCase @Inject constructor(
    private val repository: EpisodesRepository
) {

    suspend operator fun invoke() {
        repository.loadNextEpisodes()
    }
}