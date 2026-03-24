package com.example.domain.repository

import com.example.model.Character
import com.example.model.OperationResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface CharactersRepository {

    val charactersData: StateFlow<OperationResult<List<Character>>>

    suspend fun loadNextCharacters()

    fun getCharacter(id: Int): Flow<OperationResult<Character>>
}