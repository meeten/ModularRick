package com.example.ui.pagination.model

sealed class PaginatingStateScreen<out T> {

    object Loading : PaginatingStateScreen<Nothing>()

    data class Data<T>(
        val items: T,
        val isLoadNextData: Boolean = false
    ) : PaginatingStateScreen<T>()

    data class Error(
        val message: String
    ) : PaginatingStateScreen<Nothing>()
}