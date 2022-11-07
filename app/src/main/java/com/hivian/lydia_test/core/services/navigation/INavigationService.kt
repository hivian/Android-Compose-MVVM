package com.hivian.lydia_test.core.services.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.hivian.lydia_test.core.models.domain.RandomUserDomain

interface INavigationService {

    var mainNavController: NavHostController?

    var navigationActivity: AppCompatActivity?

    fun navigateBack(): Boolean

    fun openRandomUserDetail(randomUser: RandomUserDomain)

}
