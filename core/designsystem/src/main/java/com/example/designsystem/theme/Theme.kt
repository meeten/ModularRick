package com.example.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val DarkColorScheme = darkColorScheme(
    primary = lightBlue,
    surface = surfaceBackground,
    surfaceContainer = surfaceBackground,
    tertiary = Color.Red,
    background = surfaceBackground,
    primaryContainer = Color.White,
    outline = Color.White,

    onPrimary = lightBlue,
    onSecondary = Color.Black,
    onTertiary = Color.Red,
    onBackground = Color.White,
    onPrimaryContainer = Color.Black,
)

@Composable
fun RickAndMortyAppTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content,
    )
}