package com.hivian_compose_mvvm.basic_feature.domain.usecases

import com.hivian.compose_mvvm.core.services.ILocalizationService

class TranslateResourceUseCase(
    private val localizationService: ILocalizationService
) {

    operator fun invoke(text: Int): String {
        return localizationService.localizedString(text)
    }

}