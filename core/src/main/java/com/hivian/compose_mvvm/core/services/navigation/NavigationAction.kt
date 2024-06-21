package com.hivian.compose_mvvm.core.services.navigation

sealed class NavigationAction {
    object ToHomeScreen : NavigationAction()
    data class ToDetailScreen(val userId: Int) : NavigationAction()
    object Back : NavigationAction()
}
