package com.hivian.lydia_test.core.services.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.hivian.lydia_test.core.models.domain.RandomUserDomain

interface INavigationService {

    var navigationActivity: AppCompatActivity?

    @Composable
    fun InitNavController()

    fun navigateBack(): Boolean

    fun openRandomUserDetail(userId: Int)

}
