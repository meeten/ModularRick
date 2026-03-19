package com.example.model

enum class CharacterStatus(val type: String) {
    ALIVE(type = "alive"),
    DEAD(type = "dead"),
    UNKNOWN(type = "unknown")
}