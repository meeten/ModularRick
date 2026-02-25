package com.example.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

internal class CharacterDetailViewModelFactory(
    private val id: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CharacterDetailViewModel(id) as T
    }
}