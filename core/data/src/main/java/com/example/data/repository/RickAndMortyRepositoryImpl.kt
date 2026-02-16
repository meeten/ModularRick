package com.example.data.repository

import com.example.data.mapper.RickAndMortyMapper
import com.example.domain.repository.RickAndMortyRepository
import com.example.network.ApiFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

object RickAndMortyRepositoryImpl : RickAndMortyRepository {

    //TODO: inject
    private val apiService = ApiFactory.apiService
    private val rickAndMortyMapper = RickAndMortyMapper()

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    override fun getCharacter(id: Int) = flow {
        val response = apiService.getCharacter(id)
        emit(rickAndMortyMapper.mapResponseToCharacter(response))
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = null
    )
}