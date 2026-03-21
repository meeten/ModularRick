package com.example.episodes.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.model.Episode
import com.example.ui.character.CharacterNameText

@Composable
internal fun HeaderLayout(
    characterName: String,
    groupedEpisodes: Map<Int, List<Episode>>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        CharacterNameText(characterName = characterName)

        Spacer(modifier = Modifier.height(12.dp))

        VerticalEpisodesStatistics(
            groupedEpisodes = groupedEpisodes,
            modifier = Modifier.padding()
        )
    }
}
