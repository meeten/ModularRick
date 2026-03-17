package com.example.network

import com.example.model.CharacterDto
import com.example.model.CharactersDto
import com.example.model.EpisodeDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("character/{page}")
    suspend fun getCharacters(
        @Path("page") page: String = "1"
    ): CharactersDto

    @GET("character/{id}")
    suspend fun getCharacter(
        @Path("id") id: Int
    ): CharacterDto

    @GET("episode/{id}")
    suspend fun getEpisodesByIds(
        @Path("id") ids: String
    ): List<EpisodeDto>

    @GET("episode/{id}")
    suspend fun getEpisodeById(
        @Path("id") id: String
    ): EpisodeDto
}
