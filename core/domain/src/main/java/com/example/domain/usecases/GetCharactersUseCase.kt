package com.example.domain.usecases

import com.example.domain.repository.CharactersRepository
import com.example.model.Character
import com.example.model.OperationResult
import jakarta.inject.Inject
import kotlinx.coroutines.flow.StateFlow

class GetCharactersUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository
) {

    operator fun invoke(): StateFlow<OperationResult<List<Character>>> {
        return charactersRepository.charactersData
    }
}