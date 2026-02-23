package com.example.character.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.character.R
import com.example.character.models.FieldInfo
import com.example.designsystem.theme.RickAndMortyAppTheme
import com.example.model.CharacterStatus

@Composable
fun CharacterItemContent(
    status: CharacterStatus,
    name: String,
    imageUrl: String,
    fieldsInfo: List<FieldInfo>,
    modifier: Modifier = Modifier,
    imagePreview: Int? = null,
    onViewAllEpisodesClick: () -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier.verticalScroll(scrollState)
    ) {
        CharacterStatusCard(
            characterStatus = status,
            modifier = Modifier
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = name,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )

        Spacer(modifier = Modifier.height(10.dp))

        AsyncImage(
            model = imagePreview ?: imageUrl,
            contentDescription = null,
            modifier = Modifier
                .height(400.dp)
                .clip(shape = RoundedCornerShape(40.dp)),
            contentScale = ContentScale.FillBounds
        )

        Spacer(modifier = Modifier.height(15.dp))

        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            fieldsInfo.forEach { fieldInfo ->
                TitleInfoLayout(
                    title = stringResource(fieldInfo.title),
                    info = fieldInfo.info,
                    modifier = Modifier
                )
            }
        }

        ExpandEpisodesButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp),
        ) {
            onViewAllEpisodesClick()
        }
    }
}

private val modifierPreview = Modifier.padding(8.dp)
private val fieldInfoPreview = listOf(
    FieldInfo(
        title = R.string.location_info,
        info = "location"
    ),
    FieldInfo(
        title = R.string.species_info,
        info = "species"
    ),
    FieldInfo(
        title = R.string.gender_info,
        info = "gender"
    )
)

@Preview(
    name = "Alive status",
    backgroundColor = 0xFF000000,
    showBackground = true
)
@Composable
private fun CharacterItemContentWithAliveStatusPreview() {
    RickAndMortyAppTheme {
        Scaffold {
            CharacterItemContent(
                status = CharacterStatus.Alive,
                name = "Rick Sanchez",
                imageUrl = "",
                imagePreview = R.drawable.img_preview_1,
                fieldsInfo = fieldInfoPreview,
                modifier = modifierPreview.padding(it)
            ) {}
        }
    }
}

@Preview(
    name = "Dead status",
    backgroundColor = 0xFF000000,
    showBackground = true
)
@Composable
private fun CharacterItemContentWithDeadStatusPreview() {
    RickAndMortyAppTheme {
        Scaffold {
            CharacterItemContent(
                status = CharacterStatus.Dead,
                name = "Shnoopy Bloopers",
                imageUrl = "",
                imagePreview = R.drawable.img_preview_2,
                fieldsInfo = fieldInfoPreview,
                modifier = modifierPreview.padding(it)
            ) {}
        }
    }
}

@Preview(
    name = "Unknown status",
    backgroundColor = 0xFF000000,
    showBackground = true
)
@Composable
private fun CharacterItemContentWithUnknownStatusPreview() {
    RickAndMortyAppTheme {
        Scaffold {
            CharacterItemContent(
                status = CharacterStatus.Unknown,
                name = "Bootleg Portal Chemist Rick",
                imageUrl = "",
                imagePreview = R.drawable.img_preview_3,
                fieldsInfo = fieldInfoPreview,
                modifier = modifierPreview.padding(it)
            ) {}
        }
    }
}
