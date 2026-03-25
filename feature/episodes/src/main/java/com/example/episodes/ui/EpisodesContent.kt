package com.example.episodes.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import com.example.model.Episode
import com.example.ui.HorizontalEpisodesStatistics
import com.example.ui.loading.Loading

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EpisodesContent(
    groupEpisodes: Map<Int, List<Episode>>,
    uniqueCharactersCounts: IntArray,
    isLoadNextData: Boolean,
    modifier: Modifier = Modifier,
    onLoadNextData: () -> Unit
) {
    LazyColumn(modifier = modifier) {
        groupEpisodes.forEach { (episodeNumber, episodes) ->
            stickyHeader {
                StickySectionHeader(episodeNumber, uniqueCharactersCounts)
            }

            items(items = episodes, key = { it.id }) { episode ->
                HorizontalEpisodesStatistics(
                    episodeNumber = episode.episodeNumber,
                    name = episode.name,
                    airDate = episode.airDate
                )
            }
        }

        item {
            if (isLoadNextData) {
                Loading()
            } else {
                SideEffect {
                    onLoadNextData()
                }
            }
        }
    }
}