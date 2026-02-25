package com.example.model

import android.graphics.Color

enum class CharacterStatus(val type: String, val color: Int) {
    ALIVE(type = "alive", color = Color.GREEN),
    DEAD(type = "dead", color = Color.RED),
    UNKNOWN(type = "unknown", color = Color.YELLOW)
}