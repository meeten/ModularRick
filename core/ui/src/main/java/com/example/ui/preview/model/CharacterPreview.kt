package com.example.ui.preview.model

import com.example.model.CharacterStatus

data class CharacterPreview(
    val id: Int,
    val name: String,
    val status: CharacterStatus,
    val imageUrl: Int
)