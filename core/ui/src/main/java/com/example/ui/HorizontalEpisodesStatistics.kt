package com.example.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.RickAndMortyAppTheme
import com.example.ui.preview.BACKGROUND_BLUE_GRAY
import com.example.ui.preview.SHOW_BACKGROUND

@Composable
fun HorizontalEpisodesStatistics(
    episodeNumber: Int,
    name: String,
    airDate: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(vertical = 12.dp)
    ) {
        LabeledCount(
            firstLabel = stringResource(R.string.episode),
            secondLabel = "$episodeNumber",
            modifier = Modifier.weight(0.35f)
        )

        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.weight(0.65f)
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.End
            )

            Text(text = airDate)
        }
    }
}

@Preview(
    backgroundColor = BACKGROUND_BLUE_GRAY,
    showBackground = SHOW_BACKGROUND
)
@Composable
fun HorizontalEpisodesStatisticsPreview() {
    RickAndMortyAppTheme {
        HorizontalEpisodesStatistics(
            episodeNumber = 1,
            name = "Pilot",
            airDate = "December 2, 2013",
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}