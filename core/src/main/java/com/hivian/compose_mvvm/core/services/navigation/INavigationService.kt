package com.hivian.compose_mvvm.core.services.navigation

import androidx.navigation.NavHostController

interface INavigationService {

    var mainNavController: NavHostController

    fun navigateBack(): Boolean

    fun navigateTo(route: String)

}
