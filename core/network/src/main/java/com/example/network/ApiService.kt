package com.example.network

import com.example.model.CharacterDto
import com.example.model.CharactersResponseDto
import com.example.model.EpisodeDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface ApiService {

    @GET("character")
    suspend fun getCharacters(): CharactersResponseDto

    @GET
    suspend fun getCharacters(@Url fullUrl: String): CharactersResponseDto

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
