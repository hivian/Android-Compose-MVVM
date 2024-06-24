package com.hivian.randomusers.core.domain.usecases

import com.hivian.randomusers.core.domain.services.ILocalizationService

class TranslateResourceUseCase(
    private val localizationService: ILocalizationService
) {

    operator fun invoke(text: Int): String {
        return localizationService.localizedString(text)
    }

}