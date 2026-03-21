package com.example.rickandmortyapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {

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