package com.example.domain.usecases

import com.example.domain.repository.RickAndMortyRepository
import com.example.model.Episode
import com.example.model.OperationResult
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class GetEpisodesUseCase @Inject constructor(
    private val rickAndMortyRepository: RickAndMortyRepository
) {

    operator fun invoke(urls: List<String>): Flow<OperationResult<List<Episode>>> {
        val ids = urlsToIds(urls)

        if (ids.isEmpty()) return emptyFlow()
        return rickAndMortyRepository.getEpisodesByIds(ids)
    }

    private fun urlsToIds(urls: List<String>): List<String> {
        return urls.mapNotNull { url ->
            val id =
                url.substringAfterLast(
                    DELIMITER_FOR_TRANSFORMATION,
                    missingDelimiterValue = ""
                )
            if (id.isNotEmpty() && id.all { it.isDigit() }) id else null
        }
    }

    private companion object {
        const val DELIMITER_FOR_TRANSFORMATION = '/'
    }
}
