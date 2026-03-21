package com.example.home.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.RickAndMortyAppTheme
import com.example.model.Character
import com.example.ui.loading.Loading
import com.example.ui.preview.BACKGROUND_COLOR
import com.example.ui.preview.SHOW_BACKGROUND
import com.example.ui.preview.charactersPreview
import com.example.ui.preview.model.CharacterPreview

@Composable
fun CharactersContent(
    characters: List<Character>,
    isLoadNextData: Boolean,
    modifier: Modifier = Modifier,
    charactersPreview: List<CharacterPreview>? = null,
    loadNextData: () -> Unit,
    onClickCharacter: (Int) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(4.dp),
        modifier = modifier
    ) {
        if (charactersPreview != null) {
            items(items = charactersPreview, key = { it.id }) { characterPreview ->
                CharacterCard(
                    status = characterPreview.status,
                    name = characterPreview.name,
                    imageUrl = "",
                    imagePreview = characterPreview.imageUrl,
                    modifier = Modifier.padding(8.dp)
                ) {}
            }
        }

        items(items = characters, key = { it.id }) { character ->
            CharacterCard(
                status = character.status,
                name = character.name,
                imageUrl = character.imageUrl,
                modifier = Modifier.padding(8.dp),
            ) {
                onClickCharacter(character.id)
            }
        }

        item(span = { GridItemSpan(maxCurrentLineSpan) }) {
            if (isLoadNextData) {
                Loading(
                    modifier = Modifier.wrapContentHeight()
                )
            } else {
                 SideEffect {
                    loadNextData()
                }
            }
        }
    }
}

@Preview(
    backgroundColor = BACKGROUND_COLOR,
    showBackground = SHOW_BACKGROUND
)
@Composable
fun CharactersContentPreview() {
    RickAndMortyAppTheme {
        Scaffold {
            CharactersContent(
                characters = listOf(),
                charactersPreview = charactersPreview,
                isLoadNextData = false,
                modifier = Modifier.padding(it),
                loadNextData = {},
                onClickCharacter = {}
            )
        }
    }
}