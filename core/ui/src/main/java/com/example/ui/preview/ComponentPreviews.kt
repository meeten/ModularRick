package com.example.ui.preview

import com.example.model.CharacterStatus
import com.example.ui.R
import com.example.ui.preview.model.CharacterPreview

//preview property for @Preview
const val BACKGROUND_BLACK = 0xFF000000
const val BACKGROUND_BLUE_GRAY = 0xFF252d39
const val SHOW_BACKGROUND = true

//character status name for @Preview
const val ALIVE_STATUS_NAME = "Alive status"
const val DEAD_STATUS_NAME = "Dead status"
const val UNKNOWN_STATUS_NAME = "Unknown status"

//characters
val charactersPreview = (0..5).map { index ->
    val baseNames = listOf("Rick Sanchez", "Shnoopy Bloopers", "Bootleg Portal Chemist Rick")
    val images =
        listOf(R.drawable.img_preview_1, R.drawable.img_preview_2, R.drawable.img_preview_3)
    val statuses = listOf(CharacterStatus.ALIVE, CharacterStatus.DEAD, CharacterStatus.UNKNOWN)

    CharacterPreview(
        id = index,
        name = baseNames[index % 3],
        status = statuses[index % 3],
        imageUrl = images[index % 3]
    )
}