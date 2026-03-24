package com.example.domain.usecases

import com.example.domain.repository.CharactersRepository
import com.example.model.Character
import com.example.model.OperationResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDetailCharacterUseCase @Inject constructor(
    private val repository: CharactersRepository
) {

    operator fun invoke(id: Int): Flow<OperationResult<Character>> {
        return repository.getCharacter(id)
    }
}