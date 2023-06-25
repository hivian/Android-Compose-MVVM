package com.hivian.compose_mvvm

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
import com.hivian.compose_mvvm.core.services.navigation.INavigationService
import com.hivian.compose_mvvm.core.services.IUserInteractionService
import com.hivian_compose_mvvm.basic_feature.presentation.routes.BasicFeatureScreen
import com.hivian_compose_mvvm.basic_feature.presentation.themes.MainTheme
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

            MainTheme {
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
            startDestination = BasicFeatureScreen.Home.route,
        ) {
            BasicFeatureScreen.allScreens.forEach { screen ->
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
