package com.example.character.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.character.R
import com.example.designsystem.theme.RickAndMortyAppTheme
import com.example.ui.preview.BACKGROUND_COLOR
import com.example.ui.preview.SHOW_BACKGROUND

@Composable
internal fun ExpandEpisodesButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        border = BorderStroke(
            width = 2.dp,
            color = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        Text(
            text = stringResource(R.string.view_all_episodes),
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(
    backgroundColor = BACKGROUND_COLOR,
    showBackground = SHOW_BACKGROUND
)
@Composable
fun ExpandEpisodesButtonPreview() {
    RickAndMortyAppTheme {
        ExpandEpisodesButton {}
    }
}
