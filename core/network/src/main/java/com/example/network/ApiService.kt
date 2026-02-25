package com.example.network

import com.example.model.CharacterDto
import com.example.model.EpisodesDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface ApiService {

    @GET("character/{id}")
    suspend fun getCharacter(
        @Path("id") id: Int
    ): CharacterDto

    @GET
    suspend fun getEpisodeByUrl(
        @Url fullUrl: String
    ): EpisodesDto
}