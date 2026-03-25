package com.example.character.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.designsystem.theme.RickAndMortyAppTheme
import com.example.ui.preview.BACKGROUND_BLACK
import com.example.ui.preview.SHOW_BACKGROUND

@Composable
internal fun TitleInfoLayout(
    title: String,
    info: String,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            text = info,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(
    backgroundColor = BACKGROUND_BLACK,
    showBackground = SHOW_BACKGROUND
)
@Composable
private fun TitleInfoLayoutPreview() {
    RickAndMortyAppTheme {
        TitleInfoLayout(
            title = "Rick Sanchez",
            info = "Alive - Human",
            modifier = Modifier
        )
    }
}