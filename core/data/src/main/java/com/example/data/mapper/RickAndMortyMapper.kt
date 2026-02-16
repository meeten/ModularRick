package com.example.data.mapper

import com.example.model.Character
import com.example.model.CharacterDto
import com.example.model.CharacterStatus

class RickAndMortyMapper() {

    fun mapResponseToCharacter(response: CharacterDto): Character {
        return Character(
            id = response.id,
            gender = response.gender,
            name = response.name,
            species = response.species,
            status = response.status.defineCharacterStatus(),
            type = response.type,
            imageUrl = response.imageUrl
        )
    }

    private fun String.defineCharacterStatus(): CharacterStatus {
        return when (this.lowercase()) {
            "alive" -> {
                CharacterStatus.Alive
            }

            "live" -> {
                CharacterStatus.Dead
            }

            else -> {
                CharacterStatus.Unknown
            }
        }
    }
}