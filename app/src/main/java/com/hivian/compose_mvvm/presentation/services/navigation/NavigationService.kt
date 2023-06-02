package com.hivian.compose_mvvm.presentation.services.navigation

import androidx.navigation.NavHostController
import com.hivian.compose_mvvm.domain.services.INavigationService
import com.hivian.compose_mvvm.presentation.services.navigation.routes.NavScreen
import javax.inject.Inject

internal class NavigationService @Inject constructor(): INavigationService {

    override lateinit var mainNavController: NavHostController

    override fun navigateBack(): Boolean = mainNavController.popBackStack()

    override fun openRandomUserDetail(userId: Int) {
        mainNavController.navigate(NavScreen.Detail.createRouteWithArgs(userId))
    }

}
