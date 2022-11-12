package com.hivian.lydia_test.core.services.navigation

import android.app.Activity
import androidx.compose.runtime.Composable

interface INavigationService {

    var navigationActivity: Activity?

    @Composable
    fun InitNavController()

    fun navigateBack(): Boolean

    fun openRandomUserDetail(userId: Int)

}
