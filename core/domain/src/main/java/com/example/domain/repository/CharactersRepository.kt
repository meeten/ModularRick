package com.example.domain.repository

import com.example.model.Character
import com.example.model.OperationResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface CharactersRepository {

    val charactersData: StateFlow<OperationResult<List<Character>>>

    suspend fun loadNextCharacters()

    fun getCharactersByName(name: String): Flow<OperationResult<List<Character>>>

    fun getCharacter(id: Int): Flow<OperationResult<Character>>
}