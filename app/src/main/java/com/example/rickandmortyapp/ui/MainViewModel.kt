package com.example.rickandmortyapp.ui

import androidx.lifecycle.viewModelScope
import com.example.common.exception.GlobalExceptionManager
import com.example.ui.exception.ExceptionHandlerViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    globalExceptionManager: GlobalExceptionManager
) :
    ExceptionHandlerViewModel(
        globalExceptionManager
    ) {

    private val clickMutex = Mutex()

    fun onButtonClick(action: () -> Unit) {
        viewModelScope.launch {
            if (clickMutex.isLocked) return@launch
            clickMutex.withLock {
                action()
                delay(CLICK_DEBOUNCE_MILLIS)
            }
        }
    }
}

private const val CLICK_DEBOUNCE_MILLIS = 500L