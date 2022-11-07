package com.hivian.lydia_test.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hivian.lydia_test.core.servicelocator.IoC
import com.hivian.lydia_test.core.services.navigation.INavigationService
import com.hivian.lydia_test.core.services.navigation.NavRoutes
import com.hivian.lydia_test.ui.composables.Detail
import com.hivian.lydia_test.ui.composables.Home
import com.hivian.lydia_test.ui.themes.ComposetestTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposetestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navigationService = IoC.resolve<INavigationService>()
    navigationService.mainNavController = rememberNavController()

    NavHost(
        navController = navigationService.mainNavController!!,
        startDestination = NavRoutes.Home.route,
    ) {
        composable(NavRoutes.Home.route) {
            Home()
        }

        composable(NavRoutes.Detail.route) {
            Detail()
        }
    }
}
