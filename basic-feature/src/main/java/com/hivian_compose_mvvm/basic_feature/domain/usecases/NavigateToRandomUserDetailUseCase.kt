package com.hivian_compose_mvvm.basic_feature.domain.usecases

import com.hivian.compose_mvvm.core.services.navigation.INavigationService
import com.hivian_compose_mvvm.basic_feature.presentation.routes.BasicFeatureScreen
import javax.inject.Inject

class NavigateToRandomUserDetailUseCase @Inject constructor(
    private val navigationService: INavigationService
) {

    operator fun invoke(userId: Int) {
        navigationService.navigateTo(BasicFeatureScreen.Detail.createRouteWithArgs(userId))
    }

}