package com.example.domain.usecases

import com.example.domain.repository.RickAndMortyRepository
import com.example.model.Character
import com.example.model.OperationResult
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetCharacterUseCase @Inject constructor(
    private val rickAndMortyRepository: RickAndMortyRepository
) {

    operator fun invoke(id: Int): Flow<OperationResult<Character>> {
        return rickAndMortyRepository.getCharacter(id)
    }
}