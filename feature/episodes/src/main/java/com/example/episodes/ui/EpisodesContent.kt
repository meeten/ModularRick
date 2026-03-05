package com.example.episodes.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.model.Episode
import com.example.ui.CharacterImage

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun EpisodesContent(
    characterName: String,
    imageUrl: String,
    episodes: Map<Int, List<Episode>>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        item {
            HeaderLayout(
                characterName = characterName,
                episodes = episodes
            )
        }

        item {
            CharacterImage(
                imageUrl = imageUrl,
                modifier = Modifier.padding(top = 12.dp, bottom = 24.dp)
            )
        }

        episodes.keys.forEach { seasonNumber ->
            stickyHeader {
                StickySectionHeader(
                    seasonNumber = seasonNumber
                )
            }

            items(
                episodes.getOrDefault(
                    seasonNumber, emptyList()
                ), key = { it.id }) { episode ->
                HorizontalEpisodesStatistics(episode = episode)
            }
        }
    }
}