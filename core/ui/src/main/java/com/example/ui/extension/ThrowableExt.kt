package com.example.ui.extension

import java.net.UnknownHostException

fun Throwable.getFriendlyMessage(): String {
    when (this) {
        is UnknownHostException -> {
            return "No internet connection"
        }

        else -> {
            return "Something went wrong"
        }
    }
}