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
            title = R.string.location,
            info = this.location
        ),
        FieldInfo(
            title = R.string.species,
            info = this.species
        ),
        FieldInfo(
            title = R.string.gender,
            info = this.gender
        ),
        FieldInfo(
            title = R.string.origin,
            info = this.origin
        ),
        FieldInfo(
            title = R.string.episode_count,
            info = this.episodeCount.toString()
        )
    )
}