package com.example.ui.exception

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ui.exception.model.ExceptionScreenState
import com.example.ui.extension.getFriendlyMessage
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class ExceptionHandlerViewModel : ViewModel() {

    private val _errorEvents = Channel<ExceptionScreenState>()
    val errorEvent = _errorEvents.receiveAsFlow()

    fun sendErrorEvent(throwable: Throwable) {
        viewModelScope.launch {
            val message = throwable.getFriendlyMessage()
            _errorEvents.send(ExceptionScreenState.ShowError(message))
        }
    }
}