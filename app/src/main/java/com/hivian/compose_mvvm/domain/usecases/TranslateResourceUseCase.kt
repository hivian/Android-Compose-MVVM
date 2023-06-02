package com.hivian.compose_mvvm.domain.usecases

import com.hivian.compose_mvvm.domain.services.ILocalizationService
import javax.inject.Inject

class TranslateResourceUseCase @Inject constructor(
    private val localizationService: ILocalizationService
) {

    operator fun invoke(text: Int): String {
        return localizationService.localizedString(text)
    }

}