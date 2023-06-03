package com.hivian.compose_mvvm.core.services.navigation

import androidx.navigation.NavHostController
import javax.inject.Inject

internal class NavigationService @Inject constructor(): INavigationService {

    override lateinit var mainNavController: NavHostController

    override fun navigateBack(): Boolean = mainNavController.popBackStack()

    override fun navigateTo(route: String) {
        mainNavController.navigate(route)
    }

}
