package com.example.ui.character

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.RickAndMortyAppTheme
import com.example.model.CharacterStatus
import com.example.ui.extension.color
import com.example.ui.preview.SHOW_BACKGROUND

@Composable
internal fun StatusIndicator(
    color: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(18.dp)
            .clip(shape = CircleShape)
            .background(color = MaterialTheme.colorScheme.background.copy(alpha = 0.5f))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
                .clip(shape = CircleShape)
                .background(color = Color(color))
        )
    }
}

@Preview(showBackground = SHOW_BACKGROUND)
@Composable
fun StatusIndicatorLivePreview() {
    RickAndMortyAppTheme {
        StatusIndicator(color = CharacterStatus.ALIVE.color)
    }
}

@Preview(showBackground = SHOW_BACKGROUND)
@Composable
fun StatusIndicatorDeadPreview() {
    RickAndMortyAppTheme {
        StatusIndicator(color = CharacterStatus.DEAD.color)
    }
}

@Preview(showBackground = SHOW_BACKGROUND)
@Composable
fun StatusIndicatorUnknownPreview() {
    RickAndMortyAppTheme {
        StatusIndicator(color = CharacterStatus.UNKNOWN.color)
    }
}