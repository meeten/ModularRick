package com.example.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EpisodesViewModelFactory(
    private val urls: List<String>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EpisodesViewModel(urls) as T
    }
}