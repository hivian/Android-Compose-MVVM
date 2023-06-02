package com.hivian.compose_mvvm.domain.usecases

import com.hivian.compose_mvvm.domain.services.INavigationService
import javax.inject.Inject

class NavigateToRandomUserDetailUseCase @Inject constructor(
    private val navigationService: INavigationService
) {

    operator fun invoke(userId: Int) {
        navigationService.openRandomUserDetail(userId)
    }

}