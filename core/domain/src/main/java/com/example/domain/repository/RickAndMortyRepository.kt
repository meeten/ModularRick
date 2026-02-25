package com.example.domain.repository

import com.example.model.Character
import com.example.model.Episode
import com.example.model.OperationResult
import kotlinx.coroutines.flow.StateFlow

interface RickAndMortyRepository {

    fun getCharacter(id: Int): StateFlow<OperationResult<Character>>

    fun getEpisodesByUrls(urls: List<String>): StateFlow<OperationResult<List<Episode>>>
}