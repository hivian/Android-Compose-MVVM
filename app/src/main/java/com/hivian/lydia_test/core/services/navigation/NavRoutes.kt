package com.hivian.lydia_test.core.services.navigation

sealed class NavRoutes(val route: String) {
    object Home : NavRoutes("home")
    object Detail : NavRoutes("detail")
}