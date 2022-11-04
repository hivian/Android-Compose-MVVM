package com.hivian.lydia_test.presentation.detail

import androidx.lifecycle.MutableLiveData
import com.hivian.lydia_test.core.base.ViewModelBase
import com.hivian.lydia_test.core.models.domain.RandomUserDomain
import com.hivian.lydia_test.core.servicelocator.IoC
import com.hivian.lydia_test.core.services.navigation.INavigationService

class DetailViewModel(randomUser: RandomUserDomain): ViewModelBase() {

    private val navigationService: INavigationService
        get() = IoC.resolve()

    val picture = MutableLiveData(randomUser.picture)

    val name = MutableLiveData(randomUser.fullName)

    val email = MutableLiveData(randomUser.email)

    val cell = MutableLiveData(randomUser.cell)

    val phone = MutableLiveData(randomUser.phone)

    fun navigateBack() {
        navigationService.navigateBack()
    }

}
