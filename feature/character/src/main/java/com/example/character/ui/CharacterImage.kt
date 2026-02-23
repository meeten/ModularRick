package com.example.character.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import com.example.ui.Loading

@Composable
fun CharacterImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    @DrawableRes imagePreview: Int? = null
) {
    var isImageLoaded by rememberSaveable { mutableStateOf(false) }
    Box(
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = imagePreview ?: imageUrl,
            contentDescription = null,
            modifier = modifier,
            onState = {
                isImageLoaded = when (it) {
                    is AsyncImagePainter.State.Success -> true
                    else -> false
                }
            },
            contentScale = ContentScale.FillBounds
        )
        if (!isImageLoaded) {
            Loading(modifier = Modifier)
        }
    }
}


