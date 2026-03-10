package com.example.domain.usecases

import android.util.Log
import com.example.domain.repository.RickAndMortyRepository
import com.example.model.Episode
import com.example.model.OperationResult
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetEpisodesUseCase @Inject constructor(
    private val rickAndMortyRepository: RickAndMortyRepository
) {

    operator fun invoke(urls: List<String>): Flow<OperationResult<List<Episode>>> {
        val ids = urlsToIds(urls)
        return rickAndMortyRepository.getEpisodesByUrls(ids)
    }

    private fun urlsToIds(urls: List<String>): List<String> {
        val result = mutableListOf<String>()
        urls.forEach { url ->
            val modifiedUrl = url.substringAfterLast('/')
            Log.d("ModifiedURL", modifiedUrl)
            result.add(modifiedUrl)
        }
        return result
    }
}
