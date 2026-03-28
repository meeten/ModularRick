package com.example.domain.usecases

import com.example.domain.repository.CharactersRepository
import com.example.model.Character
import com.example.model.OperationResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersByNameUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository
) {

    operator fun invoke(name:String): Flow<OperationResult<List<Character>>> {
        return charactersRepository.getCharactersByName(name)
    }
}