package com.example.character_episodes.ui

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.model.Episode
import com.example.ui.HorizontalEpisodesStatistics
import com.example.ui.character.CharacterImage

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun EpisodesContent(
    characterName: String,
    imageUrl: String,
    groupedEpisodes: Map<Int, List<Episode>>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        item {
            HeaderLayout(
                characterName = characterName,
                groupedEpisodes = groupedEpisodes
            )
        }

        item {
            CharacterImage(
                imageUrl = imageUrl,
                modifier = Modifier
                    .height(400.dp)
                    .clip(shape = RoundedCornerShape(40.dp))
                    .padding(top = 12.dp, bottom = 24.dp)
            )
        }

        groupedEpisodes.forEach { (seasonNumber, episodes) ->
            Log.d("EpisodesContent", "$seasonNumber")
            stickyHeader { CharacterEpisodesStickySectionHeader(seasonNumber = seasonNumber) }

            items(items = episodes, key = { it.id }) { episode ->
                HorizontalEpisodesStatistics(
                    episodeNumber = episode.episodeNumber,
                    name = episode.name,
                    airDate = episode.airDate
                )
            }
        }
    }
}