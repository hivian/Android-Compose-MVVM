package com.hivian_compose_mvvm.basic_feature.domain.usecases

import com.hivian.compose_mvvm.core.services.navigation.INavigationService
import javax.inject.Inject

class NavigateBackUseCase @Inject constructor(
    private val navigationService: INavigationService
) {

    operator fun invoke() {
        navigationService.navigateBack()
    }

}