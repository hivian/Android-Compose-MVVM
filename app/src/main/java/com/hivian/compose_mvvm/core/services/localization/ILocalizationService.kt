package com.hivian.compose_mvvm.core.services.localization

import androidx.annotation.StringRes

interface ILocalizationService {

    fun localizedString(@StringRes key: Int): String

    fun localizedString(@StringRes key: Int, vararg arguments: Any): String

}
