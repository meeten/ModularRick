package com.example.search.model

import com.example.model.Character

sealed class SearchScreenState {

    object Initial : SearchScreenState()

    object Loading : SearchScreenState()

    data class Result(val results: List<Character>) : SearchScreenState()

    data class Error(val errorDescription: String) : SearchScreenState()
}