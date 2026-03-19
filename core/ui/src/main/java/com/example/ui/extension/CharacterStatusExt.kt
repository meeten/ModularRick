package com.example.ui.extension

import android.graphics.Color
import com.example.model.CharacterStatus

val CharacterStatus.color: Int
    get() = when (this) {
        CharacterStatus.ALIVE -> Color.GREEN
        CharacterStatus.DEAD -> Color.RED
        CharacterStatus.UNKNOWN -> Color.YELLOW
    }