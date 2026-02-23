package com.example.character.extension

import com.example.character.R
import com.example.character.models.CharacterDetailScreenState
import com.example.character.models.FieldInfo
import com.example.model.Character

internal fun Character?.asScreenState(): CharacterDetailScreenState {
    return this?.let { character ->
        CharacterDetailScreenState.CharacterDetail(
            character = character,
            fieldsInfo = character.getFieldsInfo()
        )
    } ?: CharacterDetailScreenState.Initial
}

internal fun Character.getFieldsInfo(): List<FieldInfo> {
    return listOf(
        FieldInfo(
            title = R.string.location_info,
            info = this.location
        ),
        FieldInfo(
            title = R.string.species_info,
            info = this.species
        ),
        FieldInfo(
            title = R.string.gender_info,
            info = this.gender
        ),
        FieldInfo(
            title = R.string.origin_info,
            info = this.origin
        ),
        FieldInfo(
            title = R.string.episode_count_info,
            info = this.episodeCount.toString()
        )
    )
}