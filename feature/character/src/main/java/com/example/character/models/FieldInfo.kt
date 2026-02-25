package com.example.character.models

import androidx.annotation.StringRes

internal data class FieldInfo(
    @param:StringRes val title: Int,
    val info: String
)