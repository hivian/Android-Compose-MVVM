package com.hivian.randomusers

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
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hivian.randomusers.core.domain.services.IUserInteractionService
import com.hivian.randomusers.homefeature.presentation.detail.DetailScreen
import com.hivian.randomusers.homefeature.presentation.home.HomeScreen
import com.hivian.randomusers.homefeature.presentation.themes.MainTheme
import org.koin.android.ext.android.inject

sealed class Screen(val route: String) {

    companion object {
        const val USER_ID_KEY = "user_id"
    }

    data object Home : Screen("home")
    data object Detail : Screen("detail/{$USER_ID_KEY}")

    fun loadParameterValue(key: String, userId: Int): String {
        return route.replace("{$key}", "$userId")
    }
}


class MainActivity : ComponentActivity() {

    private val userInteractionService: IUserInteractionService by inject()

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
    fun InitNavController(
        navController: NavHostController = rememberNavController()
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
        ) {
            composable(route = Screen.Home.route) {
                HomeScreen(
                    onNavigateToDetail = { userId ->
                        navController.navigate(Screen.Detail.loadParameterValue(Screen.USER_ID_KEY, userId))
                    }
                )
            }
            composable(
                route = Screen.Detail.route,
                arguments = listOf(
                    navArgument(Screen.USER_ID_KEY) { type = NavType.IntType }
                )
            ) { backStackEntry ->
                DetailScreen(
                    userId = backStackEntry.arguments!!.getInt(Screen.USER_ID_KEY),
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }

}
