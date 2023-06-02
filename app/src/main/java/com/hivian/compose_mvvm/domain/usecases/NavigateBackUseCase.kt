package com.hivian.compose_mvvm.domain.usecases

import com.hivian.compose_mvvm.domain.services.INavigationService
import javax.inject.Inject

class NavigateBackUseCase @Inject constructor(
    private val navigationService: INavigationService
) {

    operator fun invoke() {
        navigationService.navigateBack()
    }

}