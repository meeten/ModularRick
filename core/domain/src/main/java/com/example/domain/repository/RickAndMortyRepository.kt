package com.example.domain.repository

import com.example.model.Character
import com.example.model.Episode
import com.example.model.OperationResult
import kotlinx.coroutines.flow.Flow

interface RickAndMortyRepository {

    fun getCharacters(): Flow<OperationResult<List<Character>>>

    suspend fun loadNextCharacters()

    fun getCharacter(id: Int): Flow<OperationResult<Character>>

    fun getEpisodesByIds(ids: List<String>):
            Flow<OperationResult<List<Episode>>>
}