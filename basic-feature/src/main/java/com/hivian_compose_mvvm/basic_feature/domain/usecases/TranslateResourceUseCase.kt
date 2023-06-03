package com.hivian_compose_mvvm.basic_feature.domain.usecases

import com.hivian.compose_mvvm.core.services.ILocalizationService
import javax.inject.Inject

class TranslateResourceUseCase @Inject constructor(
    private val localizationService: ILocalizationService
) {

    operator fun invoke(text: Int): String {
        return localizationService.localizedString(text)
    }

}