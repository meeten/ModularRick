package com.example.model

import com.google.gson.annotations.SerializedName

data class EpisodesResponseDto(
    @SerializedName("info") val infoDto: InfoDto,
    @SerializedName("results") val episodes: List<EpisodeDto>
)