package com.hivian.lydia_test.core.services.navigation

import androidx.appcompat.app.AppCompatActivity
import com.hivian.lydia_test.business.model.domain.RandomUserDomain

interface INavigationService {

    var navigationActivity: AppCompatActivity?

    fun openRandomUserDetail(randomUser: RandomUserDomain)

}
