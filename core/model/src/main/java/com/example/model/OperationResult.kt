package com.example.model

sealed interface OperationResult<T> {

    data class Success<T>(val data: T) : OperationResult<T>

    data class Failure<T>(val throwable: Throwable) : OperationResult<T>
}