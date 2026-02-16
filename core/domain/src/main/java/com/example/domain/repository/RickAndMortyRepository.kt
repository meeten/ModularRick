package com.example.domain.repository

import com.example.model.Character
import kotlinx.coroutines.flow.StateFlow

interface RickAndMortyRepository {

    fun getCharacter(id: Int): StateFlow<Character?>
}