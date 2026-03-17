package com.example.model

import com.google.gson.annotations.SerializedName

data class CharactersDto(
    @SerializedName("info") val infoDto: InfoDto,
    @SerializedName("results") val characters: List<CharacterDto>
)
