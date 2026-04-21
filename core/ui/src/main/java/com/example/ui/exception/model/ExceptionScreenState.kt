package com.example.ui.exception.model

sealed interface ExceptionScreenState {

    data class ShowError(
        val message: String
    ) : ExceptionScreenState
}