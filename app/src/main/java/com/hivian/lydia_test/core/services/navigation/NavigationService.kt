package com.hivian.lydia_test.core.services.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

internal class NavigationService: INavigationService {

    private lateinit var mainNavController: NavHostController

    override var navigationActivity: Activity? = null

    override fun navigateBack(): Boolean = mainNavController.popBackStack()

    override fun openRandomUserDetail(userId: Int) {
        mainNavController.navigate(NavScreen.Detail.createRouteWithArgs(userId))
    }

    @Composable
    override fun InitNavController() {
        mainNavController = rememberNavController()

        NavHost(
            navController = mainNavController,
            startDestination = NavScreen.Home.route,
        ) {
            IScreenRoute.allScreens.forEach { screen ->
                composable(
                    screen.route,
                    screen.arguments,
                    screen.deepLinks
                ) {
                    screen.Content(
                        navController = mainNavController,
                        navBackStackEntry = it
                    )
                }
            }
        }
    }

}
