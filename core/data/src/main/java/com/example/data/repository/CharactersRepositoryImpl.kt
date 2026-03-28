package com.example.data.repository

import com.example.data.base.BasePagingRepository
import com.example.data.base.PageResult
import com.example.data.mapper.RickAndMortyMapper
import com.example.domain.repository.CharactersRepository
import com.example.model.Character
import com.example.network.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharactersRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: RickAndMortyMapper,
    coroutineScope: CoroutineScope,
) : BasePagingRepository<Character>(coroutineScope), CharactersRepository {

    override suspend fun fetchUrl(url: String?): PageResult<Character> {
        val response = if (url == null) {
            apiService.getCharacters()
        } else {
            apiService.getCharacters(fullUrl = url)
        }
        val characters = mapper.mapResponseToCharacters(response)
        characters.forEach { character ->
            dataCache[character.id] = character
        }
        return PageResult(
            items = characters,
            nextUrl = response.infoDto.nextPageUrl
        )
    }

    override val charactersData = data

    override suspend fun loadNextCharacters() = loadNextData()

    override fun getCharactersByName(name: String) = flow {
        val response = apiService.getCharactersByName(name)
        val characters = mapper.mapResponseToCharacters(response)
        emit(characters)
    }.asOperationResultFlow()

    override fun getCharacter(id: Int) = flow {
        val character = dataCache.getOrPut(key = id) {
            mapper.mapResponseToCharacter(apiService.getCharacter(id))
        }
        emit(character)
    }.asOperationResultFlow()
}