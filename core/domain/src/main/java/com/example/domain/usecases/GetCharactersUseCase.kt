package com.example.domain.usecases

import com.example.domain.repository.RickAndMortyRepository
import com.example.model.Character
import com.example.model.OperationResult
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class GetCharactersUseCase @Inject constructor(
    private val rickAndMortyRepository: RickAndMortyRepository
) {

    operator fun invoke(): StateFlow<OperationResult<List<Character>>> {
        return rickAndMortyRepository.charactersData
    }
}