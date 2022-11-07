package com.hivian.lydia_test.core.services.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavHostController
import com.hivian.lydia_test.core.models.domain.RandomUserDomain

internal class NavigationService: INavigationService {

    override var navigationActivity: AppCompatActivity? = null

    override var mainNavController: NavHostController? = null

    override fun navigateBack(): Boolean = mainNavController?.navigateUp() ?: false

    override fun openRandomUserDetail(randomUser: RandomUserDomain) {
        mainNavController?.navigate(NavRoutes.Detail.route)
    }

    private fun navigateTo(directions: NavDirections) {
        mainNavController?.navigate(directions)
    }

}
