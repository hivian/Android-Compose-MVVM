package com.hivian.compose_mvvm.domain.services

import androidx.navigation.NavHostController

interface INavigationService {

    var mainNavController: NavHostController

    fun navigateBack(): Boolean

    fun openRandomUserDetail(userId: Int)

}
