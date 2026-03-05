package com.example.episodes.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.episodes.R
import com.example.model.Episode

@Composable
internal fun VerticalEpisodesStatistics(
    groupedEpisodes: Map<Int, List<Episode>>,
    modifier: Modifier = Modifier
) {
    LazyRow(modifier = modifier) {
        items(items = groupedEpisodes.toList()) { episode ->
            Column(
                modifier = Modifier.padding(end = 20.dp),
                verticalArrangement = Arrangement.spacedBy(3.dp),
            ) {
                LabeledCount(
                    firstLabel = stringResource(R.string.season, episode.first),
                    secondLabel = stringResource(R.string.ep, episode.second.size),
                    modifier = Modifier.padding(end = 20.dp)
                )
            }
        }
    }
}