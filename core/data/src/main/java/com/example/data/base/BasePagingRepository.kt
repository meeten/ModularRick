package com.example.data.base

import com.example.model.OperationResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.flow.stateIn
import retrofit2.HttpException

abstract class BasePagingRepository<T>(
    coroutineScope: CoroutineScope
) {

    private val nextDataNeededEvents = MutableSharedFlow<Unit>(replay = 1)

    abstract suspend fun fetchUrl(url: String?): PageResult<T>
    protected val dataCache = mutableMapOf<Int, T>()
    private var nextFrom: String? = null
    private val loadedData = flow {
        nextDataNeededEvents.emit(Unit)
        nextDataNeededEvents.collect {
            val startFrom = nextFrom
            if (startFrom == null && dataCache.isNotEmpty()) {
                emit(dataCache.values.toList())
                return@collect
            }

            val pageResult = fetchUrl(nextFrom)
            nextFrom = pageResult.nextUrl
            emit(dataCache.values.toList())
        }
    }

    protected val data: StateFlow<OperationResult<List<T>>> = loadedData
        .map { OperationResult.Success(it) as OperationResult<List<T>> }
        .retryWhen { cause, attempt ->
            if ((cause as? HttpException)?.code() == TOO_MANY_REQUEST_CODE) {
                delay(RETRY_TIMEOUT_MILLS)
                return@retryWhen true
            }
            val shouldRetry = attempt < MAX_ATTEMPTS
            if (shouldRetry) {
                delay(RETRY_TIMEOUT_MILLS)
            }
            shouldRetry
        }
        .catch { throwable ->
            emit(OperationResult.Failure(throwable))
        }
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = OperationResult.Success(emptyList())
        )

    protected suspend fun loadNextData() {
        nextDataNeededEvents.emit(Unit)
    }

    protected fun <R> Flow<R>.asOperationResultFlow(): Flow<OperationResult<R>> {
        return this.map {
            OperationResult.Success(it) as OperationResult<R>
        }.retry(2) {
            delay(RETRY_TIMEOUT_MILLS)
            true
        }.catch { throwable ->
            OperationResult.Failure<R>(throwable)
        }
    }

    private companion object {

        const val TOO_MANY_REQUEST_CODE = 429
        const val MAX_ATTEMPTS = 3
        const val RETRY_TIMEOUT_MILLS = 3000L
    }
}