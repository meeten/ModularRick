package com.example.common.exception

import com.example.common.extenstion.getFriendlyMessage
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

data class GlobalException(
    val message: String
)

object GlobalExceptionManager {

    private val _exceptionEvents = Channel<GlobalException>()
    val exceptionEvents = _exceptionEvents.receiveAsFlow()

    suspend fun sendException(throwable: Throwable) {
        val message = throwable.getFriendlyMessage()
        _exceptionEvents.send(GlobalException(message))
    }
}