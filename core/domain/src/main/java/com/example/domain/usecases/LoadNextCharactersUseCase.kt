package com.example.domain.usecases

import com.example.domain.repository.RickAndMortyRepository
import javax.inject.Inject

class LoadNextCharactersUseCase @Inject constructor(
    private val rickAndMortyRepository: RickAndMortyRepository
) {

    suspend operator fun invoke() {
        rickAndMortyRepository.loadNextCharacters()
    }
}