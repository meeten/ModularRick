package com.example.character

import androidx.lifecycle.ViewModel
import com.example.character.extension.getFieldsInfo
import com.example.character.models.CharacterDetailScreenState
import com.example.data.repository.RickAndMortyRepositoryImpl
import com.example.domain.usecases.GetCharacterUseCase
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
                    fieldsInfo = character.getFieldsInfo()
                )
            } ?: CharacterDetailScreenState.Initial
        }
        .onStart {
            emit(CharacterDetailScreenState.Loading)
        }
}