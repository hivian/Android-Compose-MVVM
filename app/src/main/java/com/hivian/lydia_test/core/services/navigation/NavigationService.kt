package com.hivian.lydia_test.core.services.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.hivian.lydia_test.business.model.domain.RandomUserDomain
import com.hivian.lydia_test.ui.fragments.HomeFragmentDirections

internal class NavigationService: INavigationService {

    override var navigationActivity: AppCompatActivity? = null

    private val mainNavController: NavController?
        get() = navigationActivity?.supportFragmentManager?.primaryNavigationFragment?.view?.findNavController()

    override fun openRandomUserDetail(randomUser: RandomUserDomain) {
        navigateTo(HomeFragmentDirections.actionHomeFragmentToDetailFragment(randomUser))
    }

    private fun navigateTo(directions: NavDirections) {
        mainNavController?.navigate(directions)
    }

}
