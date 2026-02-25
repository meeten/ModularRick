package com.example.character.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.character.R
import com.example.designsystem.theme.RickAndMortyAppTheme
import com.example.model.CharacterStatus

@Composable
internal fun CharacterStatusCard(
    characterStatus: CharacterStatus,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        border = BorderStroke(
            width = 2.dp,
            color = Color(characterStatus.color)
        )
    ) {
        Text(
            text = stringResource(R.string.status, characterStatus.type),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview(
    name = "Alive status",
    backgroundColor = 0xFF000000,
    showBackground = true
)
@Composable
private fun CharacterStatusAliveCardPreview() {
    RickAndMortyAppTheme {
        CharacterStatusCard(
            characterStatus = CharacterStatus.ALIVE
        )
    }
}

@Preview(
    name = "Dead status",
    backgroundColor = 0xFF000000,
    showBackground = true
)
@Composable
private fun CharacterStatusDeadCardPreview() {
    RickAndMortyAppTheme {
        CharacterStatusCard(
            characterStatus = CharacterStatus.DEAD
        )
    }
}

@Preview(
    name = "Unknown status",
    backgroundColor = 0xFF000000,
    showBackground = true
)
@Composable
private fun CharacterStatusUnknownCardPreview() {
    RickAndMortyAppTheme {
        CharacterStatusCard(
            characterStatus = CharacterStatus.UNKNOWN
        )
    }
}