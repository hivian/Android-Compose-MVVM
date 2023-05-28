package com.hivian.android_mvvm_compose.ui.services.navigation.routes

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.hivian.android_mvvm_compose.ui.composables.DetailScreen
import com.hivian.android_mvvm_compose.ui.composables.HomeScreen

sealed class NavScreen {

    object Home : IScreenRoute {

        override val route: String = "home"

        @Composable
        override fun Content(navController: NavController, navBackStackEntry: NavBackStackEntry) {
            HomeScreen(hiltViewModel())
        }

    }

    object Detail : IScreenRoute {

        private const val baseRoute: String = "detail"

        const val userIdArgument = "userId"

        override val route: String = "$baseRoute/{$userIdArgument}"

        override val arguments: List<NamedNavArgument> =
            listOf(navArgument(userIdArgument) { type = NavType.IntType })

        @Composable
        override fun Content(navController: NavController, navBackStackEntry: NavBackStackEntry) {
            DetailScreen(hiltViewModel())
        }

        fun createRouteWithArgs(userId: Int): String {
            return "$baseRoute/$userId"
        }

    }

}
