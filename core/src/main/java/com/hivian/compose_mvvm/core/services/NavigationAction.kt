package com.hivian.compose_mvvm.core.services

sealed class NavigationAction {
    data class ToDetailScreen(val userId: Int) : NavigationAction()
    data object Back : NavigationAction()
}
