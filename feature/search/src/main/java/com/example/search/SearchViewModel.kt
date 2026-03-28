package com.example.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.mapToScreenState
import com.example.domain.usecases.GetCharactersByNameUseCase
import com.example.search.model.SearchScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    getCharactersByNameUseCase: GetCharactersByNameUseCase
) : ViewModel() {

    private val queryFlow = MutableStateFlow("")

    val uiState = queryFlow
        .flatMapLatest { query ->
            if (query == "") {
                return@flatMapLatest flowOf(SearchScreenState.Initial)
            }
            getCharactersByNameUseCase(query)
                .mapToScreenState(
                    onSuccess = { characters ->
                        SearchScreenState.Result(characters)
                    },
                    onError = {
                        SearchScreenState.Error(errorDescription = it.message ?: "Unknown error")
                    }
                ).onStart { emit(SearchScreenState.Loading) }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SearchScreenState.Initial
        )

    fun onChangeQuery(query: String) {
        queryFlow.value = query
    }
}