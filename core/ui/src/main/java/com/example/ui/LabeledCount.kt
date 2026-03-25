package com.example.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.RickAndMortyAppTheme
import com.example.ui.preview.BACKGROUND_BLUE_GRAY
import com.example.ui.preview.SHOW_BACKGROUND

@Composable
fun LabeledCount(
    firstLabel: String,
    secondLabel: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(3.dp),
    ) {
        Text(
            text = firstLabel,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = secondLabel,
            color = Color.White,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Preview(
    backgroundColor = BACKGROUND_BLUE_GRAY,
    showBackground = SHOW_BACKGROUND
)
@Composable
fun LabeledCountPreview() {
    RickAndMortyAppTheme {
        LabeledCount(
            firstLabel = "Episode",
            secondLabel = "20"
        )
    }
}