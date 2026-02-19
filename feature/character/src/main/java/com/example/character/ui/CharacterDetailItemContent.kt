package com.example.character.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.character.models.FieldInfo
import com.example.model.Character

@Composable
fun CharacterItemContent(
    character: Character,
    fieldsInfo: List<FieldInfo>,
    modifier: Modifier
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier.verticalScroll(scrollState)
    ) {
        CharacterStatusCard(
            characterStatus = character.status,
            modifier = Modifier
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = character.name,
            fontSize = 30.sp,
            color = MaterialTheme.colorScheme.onPrimary
        )

        Spacer(modifier = Modifier.height(10.dp))

        AsyncImage(
            model = character.imageUrl,
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
                    info = fieldInfo.info
                )
            }
        }

        Button(
            onClick = {},
            border = BorderStroke(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(22.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        ) {
            Text(
                text = "View all episodes",
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center
            )
        }
    }
}