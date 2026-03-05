package com.example.episodes.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.episodes.R
import com.example.model.Episode

@Composable
internal fun HorizontalEpisodesStatistics(
    episode: Episode,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(vertical = 12.dp)
    ) {
        LabeledCount(
            firstLabel = stringResource(R.string.episode),
            secondLabel = episode.episodeNumber.toString(),
            modifier = Modifier.weight(0.35f)
        )

        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.weight(0.65f)
        ) {
            Text(
                text = episode.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )

            Text(text = episode.airDate)
        }
    }
}