package com.hivian_compose_mvvm.basic_feature.presentation.routes

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.hivian.compose_mvvm.core.services.navigation.INavigationScreen
import com.hivian_compose_mvvm.basic_feature.presentation.detail.DetailScreen
import com.hivian_compose_mvvm.basic_feature.presentation.home.HomeScreen

sealed class BasicFeatureScreen {

    companion object {
        val allScreens = listOf(
            Home,
            Detail,
        )
    }

    object Home : INavigationScreen {

        override val route: String = "home"

        @Composable
        override fun Content(navController: NavController, navBackStackEntry: NavBackStackEntry) {
            HomeScreen(hiltViewModel())
        }

    }

     object Detail : INavigationScreen {

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
