package com.example.character

import androidx.lifecycle.ViewModel
import com.example.character.models.CharacterDetailScreenState
import com.example.character.models.FieldInfo
import com.example.data.repository.RickAndMortyRepositoryImpl
import com.example.domain.usecases.GetCharacterUseCase
import com.example.model.Character
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class CharacterDetailViewModel(
    id: Int
) : ViewModel() {

    //TODO: inject
    private val repository = RickAndMortyRepositoryImpl
    private val getCharacterUseCase = GetCharacterUseCase(repository)

    val uiState = getCharacterUseCase(id)
        .map {
            it?.let { character ->
                CharacterDetailScreenState.CharacterDetail(
                    character = character,
                    fieldsInfo = getFieldsInfo(character)
                )
            } ?: CharacterDetailScreenState.Initial
        }
        .onStart {
            emit(CharacterDetailScreenState.Loading)
        }

    private fun getFieldsInfo(
        character: Character
    ): List<FieldInfo> {
        return listOf(
            FieldInfo(
                title = R.string.location_info,
                info = character.location
            ),
            FieldInfo(
                title = R.string.species_info,
                info = character.species
            ),
            FieldInfo(
                title = R.string.gender_info,
                info = character.gender
            ),
            FieldInfo(
                title = R.string.origin_info,
                info = character.origin
            ),
            FieldInfo(
                title = R.string.episode_count_info,
                info = character.episodeCount.toString()
            )
        )
    }
}