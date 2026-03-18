package com.example.domain.usecases

import com.example.domain.repository.RickAndMortyRepository
import com.example.model.Character
import com.example.model.OperationResult
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetCharactersUseCase @Inject constructor(
    private val rickAndMortyRepository: RickAndMortyRepository
) {

    operator fun invoke(): Flow<OperationResult<List<Character>>> {
        return rickAndMortyRepository.getCharacters()
    }
}