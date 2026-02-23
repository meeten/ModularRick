package com.example.domain.repository

import com.example.model.Character
import com.example.model.OperationResult
import kotlinx.coroutines.flow.StateFlow

interface RickAndMortyRepository {

    fun getCharacter(id: Int): StateFlow<OperationResult<Character>>
}