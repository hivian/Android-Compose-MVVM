package com.hivian.compose_mvvm.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hivian.compose_mvvm.domain.services.INavigationService
import com.hivian.compose_mvvm.domain.services.IUserInteractionService
import com.hivian.compose_mvvm.presentation.services.navigation.routes.IScreenRoute
import com.hivian.compose_mvvm.presentation.services.navigation.routes.NavScreen
import com.hivian.compose_mvvm.presentation.themes.ComposetestTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var navigationService: INavigationService

    @Inject lateinit var userInteractionService: IUserInteractionService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val snackbarHostState = remember { SnackbarHostState() }

            ComposetestTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        InitNavController()
                        userInteractionService.snackbarHostState = snackbarHostState
                    }
                }

            }
        }
    }

    @Composable
    fun InitNavController() {
        navigationService.mainNavController = rememberNavController()

        NavHost(
            navController = navigationService.mainNavController,
            startDestination = NavScreen.Home.route,
        ) {
            IScreenRoute.allScreens.forEach { screen ->
                composable(
                    screen.route,
                    screen.arguments,
                    screen.deepLinks
                ) {
                    screen.Content(
                        navController = navigationService.mainNavController,
                        navBackStackEntry = it
                    )
                }
            }
        }
    }

}
