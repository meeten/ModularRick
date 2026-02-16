package com.example.rickandmortyapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.RickAndMortyRepositoryImpl
import com.example.domain.usecases.GetCharacterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val id: Int
) : ViewModel() {

    //TODO: inject
    private val repository = RickAndMortyRepositoryImpl
    private val getCharacterUseCase = GetCharacterUseCase(repository)

    val uiState = getCharacterUseCase(id)
        .map {
            it?.let {
                CharacterScreenState.Character(it)
            } ?: CharacterScreenState.Initial
        }
        .onStart {
            emit(CharacterScreenState.Loading)
        }
}