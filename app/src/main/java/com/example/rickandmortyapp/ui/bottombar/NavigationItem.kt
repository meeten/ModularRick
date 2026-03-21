package com.example.rickandmortyapp.ui.bottombar

import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.navigation.Screen

sealed class NavigationItem(
    val screen: Screen,
    val title: String,
    val src: Int
) {
    object Home : NavigationItem(
        screen = Screen.Home,
        title = "Home",
        src = R.drawable.home_24px
    )

    object Episodes : NavigationItem(
        screen = Screen.AllEpisodesScreen,
        title = "Episodes",
        src = R.drawable.play_arrow_24px
    )

    object Search : NavigationItem(
        screen = Screen.Search,
        title = "Search",
        src = R.drawable.search_24px
    )
}
