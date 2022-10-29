package com.hivian.lydia_test.core.services.navigation

import androidx.appcompat.app.AppCompatActivity
import com.hivian.lydia_test.core.models.domain.RandomUserDomain

interface INavigationService {

    var navigationActivity: AppCompatActivity?

    fun navigateBack(): Boolean

    fun openRandomUserDetail(randomUser: RandomUserDomain)

}
