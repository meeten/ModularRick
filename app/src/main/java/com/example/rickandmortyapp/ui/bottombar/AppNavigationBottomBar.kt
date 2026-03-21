package com.example.rickandmortyapp.ui.bottombar

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.rickandmortyapp.navigation.NavigationState

@Composable
fun AppNavigationBottomBar(
    navigationState: NavigationState,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry = navigationState.navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry.value?.destination


    val navigationItems = listOf(
        NavigationItem.Home,
        NavigationItem.Episodes,
        NavigationItem.Search
    )

    BottomAppBar(modifier) {
        navigationItems.forEach { item ->
            val selected = currentDestination?.hierarchy?.any {
                it.route == item.screen.route
            } == true
            NavigationBarItem(
                selected = selected,
                onClick = { navigationState.navigateTo(item.screen.route) },
                icon = { Icon(painter = painterResource(item.src), contentDescription = null) },
                label = { Text(text = item.title) },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.surfaceContainer,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedTextColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.1f),
                    unselectedIconColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.1f),
                )
            )
        }
    }
}