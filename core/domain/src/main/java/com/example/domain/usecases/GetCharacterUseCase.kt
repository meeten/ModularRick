package com.example.domain.usecases

import com.example.domain.repository.RickAndMortyRepository
import com.example.model.Character
import com.example.model.OperationResult
import kotlinx.coroutines.flow.StateFlow

class GetCharacterUseCase(
    private val rickAndMortyRepository: RickAndMortyRepository
) {

    operator fun invoke(id: Int): StateFlow<OperationResult<Character>> {
        return rickAndMortyRepository.getCharacter(id)
    }
}