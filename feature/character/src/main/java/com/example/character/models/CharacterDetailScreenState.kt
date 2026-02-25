package com.example.character.models

internal sealed class CharacterDetailScreenState {

    object Initial : CharacterDetailScreenState()

    object Loading : CharacterDetailScreenState()

    data class CharacterDetail(
        val character: com.example.model.Character,
        val fieldsInfo: List<FieldInfo>
    ) : CharacterDetailScreenState()

    data class Error(
        val errorDescription: String
    ) : CharacterDetailScreenState()
}