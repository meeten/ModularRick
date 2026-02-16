package com.example.model

import android.graphics.Color

sealed class CharacterStatus(val type: String, val color: Int) {
    object Alive : CharacterStatus(type = "alive", color = Color.GREEN)
    object Dead : CharacterStatus(type = "dead", color = Color.RED)
    object Unknown : CharacterStatus(type = "unknown", color = Color.YELLOW)
}