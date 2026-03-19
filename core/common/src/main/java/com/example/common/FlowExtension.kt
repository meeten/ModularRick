package com.example.common

import com.example.model.OperationResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

inline fun <T, R> Flow<OperationResult<T>>.mapToScreenState(
    crossinline onSuccess: (T) -> R,
    crossinline onError: (Throwable) -> R
): Flow<R> = map { result ->
    when (result) {
        is OperationResult.Success -> {
            onSuccess(result.data)
        }

        is OperationResult.Failure -> {
            onError(result.throwable)
        }
    }
}