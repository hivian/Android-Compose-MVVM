package com.hivian.randomusers.core.presentation.navigation

sealed class NavigationAction {
    data class ToDetailScreen(val userId: Int) : NavigationAction()
    data object Back : NavigationAction()
}
