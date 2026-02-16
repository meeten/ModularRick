package com.example.rickandmortyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: CharacterViewModel = viewModel(
                factory = CharacterViewModelFactory(5)
            )
            val uiState = viewModel.uiState.collectAsState(
                CharacterScreenState.Initial
            )

            Scaffold {
                Column(
                    modifier = Modifier.padding(it)
                ) {
                    when (val currentState = uiState.value) {
                        CharacterScreenState.Initial -> {}
                        CharacterScreenState.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    color = Color.Black
                                )
                            }
                        }

                        is CharacterScreenState.Character -> {
                            Column(
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Card(
                                    border = BorderStroke(
                                        width = 2.dp,
                                        color = Color(currentState.character.status.color)
                                    )
                                ) {
                                    Text(
                                        text = currentState.character.status.type,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                                Text(
                                    text = currentState.character.name,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}