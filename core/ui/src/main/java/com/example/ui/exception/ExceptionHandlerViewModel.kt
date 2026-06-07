package com.example.ui.exception

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.exception.GlobalExceptionManager
import kotlinx.coroutines.launch

abstract class ExceptionHandlerViewModel(
    val globalExceptionManager: GlobalExceptionManager
) : ViewModel() {

    fun sendErrorEvent(throwable: Throwable) {
        viewModelScope.launch {
            globalExceptionManager.sendException(throwable)
        }
    }
}