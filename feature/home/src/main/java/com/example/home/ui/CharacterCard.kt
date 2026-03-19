package com.example.home.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.theme.RickAndMortyAppTheme
import com.example.model.CharacterStatus
import com.example.ui.CharacterImage
import com.example.ui.CharacterNameText
import com.example.ui.preview.ALIVE_STATUS_NAME
import com.example.ui.preview.DEAD_STATUS_NAME
import com.example.ui.preview.UNKNOWN_STATUS_NAME
import com.example.ui.preview.charactersPreview

@Composable
fun CharacterCard(
    status: CharacterStatus,
    name: String,
    imageUrl: String,
    modifier: Modifier = Modifier,
    @DrawableRes imagePreview: Int? = null,
    onClickCharacterCard: () -> Unit,
) {
    Card(
        modifier = modifier.clickable { onClickCharacterCard() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        border = BorderStroke(
            width = 2.dp,
            color = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Box {
            CharacterImage(
                imageUrl = imageUrl,
                imagePreview = imagePreview,
                modifier = Modifier.aspectRatio(1f),
                contentScale = ContentScale.FillBounds,
            )
            StatusIndicator(
                color = status.color,
                modifier = Modifier.padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        CharacterNameText(
            characterName = name,
            maxLines = 1,
            fontSize = 22.sp,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview(name = ALIVE_STATUS_NAME)
@Composable
fun CharacterCardAlivePreview() {
    RickAndMortyAppTheme {
        CharacterCard(
            status = charactersPreview[0].status,
            name = charactersPreview[0].name,
            imageUrl = "",
            imagePreview = charactersPreview[0].imageUrl,
            modifier = Modifier.padding(8.dp),
        ) {}
    }
}

@Preview(name = DEAD_STATUS_NAME)
@Composable
fun CharacterDeadCardPreview() {
    RickAndMortyAppTheme {
        CharacterCard(
            status = charactersPreview[1].status,
            name = charactersPreview[1].name,
            imageUrl = "",
            imagePreview = charactersPreview[1].imageUrl,
            modifier = Modifier.padding(8.dp),
        ) {}
    }
}

@Preview(name = UNKNOWN_STATUS_NAME)
@Composable
fun CharacterUnknownCardPreview() {
    RickAndMortyAppTheme {
        CharacterCard(
            status = charactersPreview[2].status,
            name = charactersPreview[2].name,
            imageUrl = "",
            imagePreview = charactersPreview[2].imageUrl,
            modifier = Modifier.padding(8.dp),
        ) {}
    }
}