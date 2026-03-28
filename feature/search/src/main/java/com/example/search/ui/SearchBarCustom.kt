package com.example.search.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.RickAndMortyAppTheme
import com.example.search.R
import com.example.ui.preview.BACKGROUND_BLUE_GRAY
import com.example.ui.preview.SHOW_BACKGROUND

@Composable
internal fun SearchBarCustom(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    var query by rememberSaveable { mutableStateOf("") }
    var isQuery by remember { mutableStateOf(false) }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .focusRequester(focusRequester),
            value = query,
            onValueChange = {
                query = it
                isQuery = true
                onSearch(it)
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                focusedTextColor = MaterialTheme.colorScheme.onSecondary,
                cursorColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.search_24px),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        )

        if (isQuery) {
            Icon(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable {
                        query = ""
                        isQuery = false
                        onSearch(query)
                    },
                painter = painterResource(R.drawable.delete_48px),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Preview(
    showBackground = SHOW_BACKGROUND,
    backgroundColor = BACKGROUND_BLUE_GRAY
)
@Composable
fun SearchBarCustomPreview() {
    RickAndMortyAppTheme {
        SearchBarCustom() { }
    }
}