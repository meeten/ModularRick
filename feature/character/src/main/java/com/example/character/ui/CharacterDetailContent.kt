package com.example.character.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.character.R
import com.example.character.models.FieldInfo
import com.example.designsystem.theme.RickAndMortyAppTheme
import com.example.model.CharacterStatus
import com.example.ui.character.CharacterImage
import com.example.ui.character.CharacterNameText
import com.example.ui.preview.ALIVE_STATUS_NAME
import com.example.ui.preview.BACKGROUND_COLOR
import com.example.ui.preview.DEAD_STATUS_NAME
import com.example.ui.preview.SHOW_BACKGROUND
import com.example.ui.preview.UNKNOWN_STATUS_NAME
import com.example.ui.preview.charactersPreview

@Composable
internal fun CharacterDetailContent(
    status: CharacterStatus,
    name: String,
    imageUrl: String,
    fieldsInfo: List<FieldInfo>,
    modifier: Modifier = Modifier,
    @DrawableRes imagePreview: Int? = null,
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

        CharacterNameText(characterName = name)

        Spacer(modifier = Modifier.height(10.dp))

        CharacterImage(
            imageUrl = imageUrl,
            modifier = Modifier
                .height(400.dp)
                .clip(shape = RoundedCornerShape(40.dp)),
            contentScale = ContentScale.FillBounds,
            imagePreview = imagePreview
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
            onClick = onViewAllEpisodesClick
        )
    }
}

private val modifierPreview = Modifier.padding(16.dp)
private val fieldInfoPreview = listOf(
    FieldInfo(
        title = R.string.location,
        info = "location"
    ),
    FieldInfo(
        title = R.string.species,
        info = "species"
    ),
    FieldInfo(
        title = R.string.gender,
        info = "gender"
    )
)

@Preview(
    name = ALIVE_STATUS_NAME,
    backgroundColor = BACKGROUND_COLOR,
    showBackground = SHOW_BACKGROUND
)
@Composable
private fun CharacterDetailContentWithAliveStatusPreview() {
    RickAndMortyAppTheme {
        Scaffold {
            CharacterDetailContent(
                status = charactersPreview[0].status,
                name = charactersPreview[0].name,
                imageUrl = "",
                imagePreview = charactersPreview[0].imageUrl,
                fieldsInfo = fieldInfoPreview,
                modifier = modifierPreview.padding(it)
            ) {}
        }
    }
}

@Preview(
    name = DEAD_STATUS_NAME,
    backgroundColor = BACKGROUND_COLOR,
    showBackground = SHOW_BACKGROUND
)
@Composable
private fun CharacterDetailContentWithDeadStatusPreview() {
    RickAndMortyAppTheme {
        Scaffold {
            CharacterDetailContent(
                status = charactersPreview[1].status,
                name = charactersPreview[1].name,
                imageUrl = "",
                imagePreview = charactersPreview[1].imageUrl,
                fieldsInfo = fieldInfoPreview,
                modifier = modifierPreview.padding(it)
            ) {}
        }
    }
}

@Preview(
    name = UNKNOWN_STATUS_NAME,
    backgroundColor = BACKGROUND_COLOR,
    showBackground = SHOW_BACKGROUND
)
@Composable
private fun CharacterDetailContentWithUnknownStatusPreview() {
    RickAndMortyAppTheme {
        Scaffold {
            CharacterDetailContent(
                status = charactersPreview[2].status,
                name = charactersPreview[2].name,
                imageUrl = "",
                imagePreview = charactersPreview[2].imageUrl,
                fieldsInfo = fieldInfoPreview,
                modifier = modifierPreview.padding(it)
            ) {}
        }
    }
}