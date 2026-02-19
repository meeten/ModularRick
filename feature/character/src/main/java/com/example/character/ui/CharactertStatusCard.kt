package com.example.character.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CharacterStatus

@Composable
fun CharacterStatusCard(
    characterStatus: CharacterStatus,
    modifier: Modifier
) {
    Card(
        modifier = modifier,
        border = BorderStroke(
            width = 2.dp,
            color = Color(characterStatus.color)
        )
    ) {
        Text(
            text = "Status: ${characterStatus.type}",
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier.padding(8.dp)
        )
    }
}