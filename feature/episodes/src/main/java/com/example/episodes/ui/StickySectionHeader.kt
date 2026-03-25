package com.example.episodes.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.episodes.R

@Composable
fun StickySectionHeader(
    episodeNumber: Int,
    uniqueCharactersCounts: IntArray,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.background(
            color = MaterialTheme.colorScheme.background
        )
    ) {
        Text(
            text = stringResource(R.string.season, episodeNumber),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = stringResource(
                R.string.unique_characters,
                uniqueCharactersCounts[episodeNumber]
            ),
            style = MaterialTheme.typography.titleLarge
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 4.dp,
            color = MaterialTheme.colorScheme.primary
        )
    }
}