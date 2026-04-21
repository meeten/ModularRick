package com.example.ui.pagination

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.mapToScreenState
import com.example.model.OperationResult
import com.example.ui.pagination.model.PaginatingStateScreen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

abstract class PaginatingViewModel<T> : ViewModel() {

    private val isLoadNextDataFlow = MutableStateFlow(false)

    protected fun <Data> createPaginatingUiState(
        useCase: () -> Flow<OperationResult<Data>>,
    ): Flow<PaginatingStateScreen<Data>> {
        return useCase()
            .mapToScreenState(
                onSuccess = { data ->
                    if (data is List<*> && data.isEmpty()) {
                        PaginatingStateScreen.Loading
                    } else {
                        PaginatingStateScreen.Data(items = data)
                    }
                },
                onError = {
                    PaginatingStateScreen.Error(message = it.message ?: "Unknown error")
                }
            ).combine(isLoadNextDataFlow) { state, isLoadNextData ->
                when (state) {
                    is PaginatingStateScreen.Data -> {
                        state.copy(
                            items = state.items,
                            isLoadNextData = isLoadNextData
                        )
                    }

                    else -> {
                        state
                    }
                }
            }.onStart {
                emit(PaginatingStateScreen.Loading)
            }
    }

    protected fun loadNextData(
        loadNextDataUseCase: suspend () -> Unit
    ) {
        viewModelScope.launch {
            isLoadNextDataFlow.value = true
            try {
                loadNextDataUseCase()
            } finally {
                isLoadNextDataFlow.value = false
            }
        }
    }
}