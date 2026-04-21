package com.example.common.extenstion

import java.net.UnknownHostException

fun Throwable.getFriendlyMessage(): String {
    return when (this) {
        is UnknownHostException -> {
            "No internet connection"
        }

        else -> {
            "Something went wrong"
        }
    }
}