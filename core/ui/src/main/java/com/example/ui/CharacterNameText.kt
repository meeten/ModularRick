package com.example.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CharacterNameText(
    characterName: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = characterName,
        style = MaterialTheme.typography.headlineLarge,
        color = MaterialTheme.colorScheme.onPrimary,
        modifier = modifier
    )
}