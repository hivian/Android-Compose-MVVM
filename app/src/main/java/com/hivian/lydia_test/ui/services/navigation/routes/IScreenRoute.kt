package com.hivian.lydia_test.ui.services.navigation.routes

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink

sealed interface IScreenRoute {

    companion object {
        val allScreens = listOf(
            NavScreen.Home,
            NavScreen.Detail,
        )
    }

    val route: String

    val arguments: List<NamedNavArgument>
        get() = emptyList()

    val deepLinks: List<NavDeepLink>
        get() = emptyList()

    @Composable
    fun Content(
        navController: NavController,
        navBackStackEntry: NavBackStackEntry
    )

}