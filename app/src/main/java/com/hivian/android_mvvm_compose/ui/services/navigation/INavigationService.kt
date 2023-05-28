package com.hivian.android_mvvm_compose.ui.services.navigation

import androidx.compose.runtime.Composable

interface INavigationService {

    @Composable
    fun InitNavController()

    fun navigateBack(): Boolean

    fun openRandomUserDetail(userId: Int)

}
