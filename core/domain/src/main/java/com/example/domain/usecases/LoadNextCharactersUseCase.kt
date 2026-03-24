package com.example.domain.usecases

import com.example.domain.repository.CharactersRepository
import javax.inject.Inject

class LoadNextCharactersUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository
) {

    suspend operator fun invoke() {
        charactersRepository.loadNextCharacters()
    }
}