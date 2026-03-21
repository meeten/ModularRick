package com.example.ui.topbar

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.ui.R

@Composable
fun NavigationIconBack(
    modifier: Modifier = Modifier,
    onClickIcon: () -> Unit
) {
    IconButton(
        onClick = onClickIcon,
        modifier = modifier,
    ) {
        Icon(
            painter = painterResource(R.drawable.arrow_back_30px),
            contentDescription = null,
        )
    }
}