package com.example.domain.usecases

import android.util.Log
import com.example.domain.repository.RickAndMortyRepository
import com.example.model.Episode
import com.example.model.OperationResult
import kotlinx.coroutines.flow.StateFlow

class GetEpisodesUseCase(
    private val rickAndMortyRepository: RickAndMortyRepository
) {

    operator fun invoke(urls: List<String>): StateFlow<OperationResult<List<Episode>>> {
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
