package com.example.ui.topbar

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarCustom(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
) {
    Column {
        TopAppBar(
            modifier = modifier.padding(horizontal = 8.dp),
            title = {
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    text = stringResource(title),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = navigationIcon
        )

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 2.dp,
            color = MaterialTheme.colorScheme.primary
        )
    }
}