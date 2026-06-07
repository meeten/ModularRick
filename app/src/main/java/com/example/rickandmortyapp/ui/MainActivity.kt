package com.example.rickandmortyapp.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.designsystem.theme.RickAndMortyAppTheme
import com.example.rickandmortyapp.MainScreen
import com.example.rickandmortyapp.navigation.rememberNavigationState
import com.example.rickandmortyapp.ui.bottombar.AppNavigationBottomBar
import com.example.ui.exception.model.ExceptionScreenState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val viewModel: MainViewModel = hiltViewModel()
            val navigationState = rememberNavigationState()
            val snackbarHostState = remember { SnackbarHostState() }

            LaunchedEffect(Unit) {
                viewModel.globalExceptionManager.exceptionEvents.collect { exception ->
                    snackbarHostState.currentSnackbarData?.dismiss()
                    snackbarHostState.showSnackbar(
                        exception.message,
                        withDismissAction = true
                    )
                }
            }

            RickAndMortyAppTheme {
                Scaffold(
                    bottomBar = {
                        AppNavigationBottomBar(navigationState = navigationState)
                    },
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
                ) {
                    MainScreen(
                        navigationState = navigationState,
                        modifier = Modifier.padding(top = it.calculateTopPadding()),
                        onClickBack = {
                            viewModel.onButtonClick { navigationState.navController.popBackStack() }
                        }
                    )
                }
            }
        }
    }
}